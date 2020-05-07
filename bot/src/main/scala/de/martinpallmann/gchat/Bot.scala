/*
 * Copyright 2020 Martin Pallmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.martinpallmann.gchat

import java.time.Instant

import cats.data.Kleisli
import cats.effect.{ExitCode, IO, IOApp}
import de.martinpallmann.gchat.BotRequest.{
  AddedToSpace,
  CardClicked,
  MessageReceived,
  RemovedFromSpace
}
import de.martinpallmann.gchat.bot.{Auth, Config}
import de.martinpallmann.gchat.gen.{FormAction, Message, Space, User}
import de.martinpallmann.gchat.circe._
import org.http4s.blaze.util.Execution
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{DefaultServiceErrorHandler, Router}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.{AuthedRequest, HttpRoutes, Request, Response}
import org.http4s.implicits._

trait Bot extends IOApp {

  def onAddedToSpace(
    eventTime: Instant,
    space: Space,
    user: User
  ): Message

  def onRemovedFromSpace(
    eventTime: Instant,
    space: Space,
    user: User
  ): Unit

  def onMessageReceived(
    eventTime: Instant,
    space: Space,
    message: Message,
    user: User
  ): Message

  def onCardClicked(
    eventTime: Instant,
    space: Space,
    message: Message,
    user: User,
    action: FormAction
  ): Message

  private val dsl: Http4sDsl[IO] = new Http4sDsl[IO] {}
  import dsl._

  private val eventHandling: BotRequest => Message = {
    case AddedToSpace(t, s, _, u) =>
      onAddedToSpace(t, s, u)
    case RemovedFromSpace(t, s, u) =>
      onRemovedFromSpace(t, s, u)
      BotResponse.empty
    case MessageReceived(t, s, m, u) =>
      onMessageReceived(t, s, m, u)
    case CardClicked(t, s, m, u, a) =>
      onCardClicked(t, s, m, u, a)
  }

  final val service
    : PartialFunction[AuthedRequest[IO, Unit], IO[Response[IO]]] = {
    case r @ POST -> Root as _ =>
      for {
        evt <- r.req.as[BotRequest]
        resp <- Ok(eventHandling(evt))
      } yield resp
  }

  def routes: HttpRoutes[IO] =
    Router("/" -> Auth(config.authConfig).routes(service))

  def httpApp: Kleisli[IO, Request[IO], Response[IO]] =
    routes.orNotFound

  def config: Config = Config()

  def init(): IO[Unit] = IO.unit

  def errorHandler
    : Request[IO] => PartialFunction[Throwable, IO[Response[IO]]] =
    DefaultServiceErrorHandler[IO]

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- init()
      res <-
        BlazeServerBuilder[IO](Execution.trampoline)
          .withBanner(config.banner)
          .bindHttp(config.port, config.ipAddress)
          .withHttpApp(httpApp)
          .withServiceErrorHandler(errorHandler)
          .resource
          .use(_ => IO.never)
          .as(ExitCode.Success)
    } yield res
}

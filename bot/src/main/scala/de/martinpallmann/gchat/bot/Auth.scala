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

package de.martinpallmann.gchat.bot

import cats.data.{Kleisli, OptionT}
import cats.{Applicative, Defer}
import cats.effect.IO
import de.martinpallmann.gchat.bot.auth.{AuthConfig, AuthImpl, NoAuth}
import org.http4s.dsl.io.Forbidden
import org.http4s.{AuthedRequest, AuthedRoutes, HttpRoutes, Response}
import org.slf4j.LoggerFactory
import org.http4s.dsl.io._

trait Auth {

  private val logger = LoggerFactory.getLogger(getClass)

  protected val onFailure: AuthedRoutes[String, IO] =
    Kleisli(req => {
      logger.debug(req.context)
      OptionT.liftF(Forbidden())
    })

  def routes(
    pf: PartialFunction[AuthedRequest[IO, Unit], IO[Response[IO]]]
  )(implicit F: Defer[IO],
    FA: Applicative[IO]
  ): HttpRoutes[IO]
}

object Auth {
  def apply(authConfig: AuthConfig): Auth =
    if (!authConfig.enabled) NoAuth
    else
      authConfig.googleProjectNumber
        .map(AuthImpl.apply)
        .getOrElse(
          throw new IllegalArgumentException(
            "Auth is enabled but no google project number is provided."
          )
        )
}

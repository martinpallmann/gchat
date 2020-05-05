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

package de.martinpallmann.gchat.bot.auth

import cats.data.Kleisli
import cats.effect.IO
import cats.{Applicative, Defer}
import de.martinpallmann.gchat.bot.Auth
import org.http4s.server.AuthMiddleware
import org.http4s._

object NoAuth extends Auth {

  private val noAuthRequest: Kleisli[IO, Request[IO], Either[String, Unit]] =
    Kleisli(_ => IO(Right(())))

  def routes(
    pf: PartialFunction[AuthedRequest[IO, Unit], IO[Response[IO]]]
  )(implicit F: Defer[IO],
    FA: Applicative[IO]
  ): HttpRoutes[IO] =
    AuthMiddleware(noAuthRequest, onFailure).apply(AuthedRoutes.of(pf))
}

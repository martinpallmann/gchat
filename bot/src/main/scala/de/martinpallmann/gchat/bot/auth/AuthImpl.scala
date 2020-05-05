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

import cats.data._
import cats.effect._
import cats.{Applicative, Defer}
import de.martinpallmann.gchat.bot.Auth
import org.http4s.headers.Authorization
import org.http4s.server._
import org.http4s.{AuthedRoutes, _}

class AuthImpl(jwt: Jwt) extends Auth {

  private def verifyToken: Kleisli[IO, Credentials, Either[String, Unit]] =
    Kleisli {
      case Credentials.Token(_, t) =>
        for {
          verified <- jwt.verify(t)
        } yield Either.cond(verified, (), "Token not verified")
      case _ => IO(Left("Credentials don't have a token."))
    }

  private def credentials(request: Request[IO]): Either[String, Credentials] =
    for {
      h <-
        request.headers
          .get(Authorization)
          .toRight(s"No valid authorization header found.")
    } yield h.credentials

  private val authRequest: Kleisli[IO, Request[IO], Either[String, Unit]] =
    Kleisli(
      request =>
        (for {
          c <- EitherT(IO(credentials(request)))
          r <- EitherT(verifyToken(c))
        } yield r).value
    )

  def routes(
    pf: PartialFunction[AuthedRequest[IO, Unit], IO[Response[IO]]]
  )(implicit F: Defer[IO],
    FA: Applicative[IO]
  ): HttpRoutes[IO] = {
    AuthMiddleware(authRequest, onFailure).apply(AuthedRoutes.of(pf))
  }
}

object AuthImpl {
  def apply(googleProjectNo: String): Auth =
    new AuthImpl(Jwt(googleProjectNo))
}

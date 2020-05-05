package de.martinpallmann.gchat.bot

import cats.{Applicative, Defer}
import cats.effect._
import cats.data._
import de.martinpallmann.gchat.bot.auth.Jwt
import org.http4s.{AuthedRoutes, _}
import org.http4s.dsl.io._
import org.http4s.headers.Authorization
import org.http4s.server._
import org.slf4j.LoggerFactory

object Auth {

  private val logger = LoggerFactory.getLogger(getClass)

  private def verifyToken: Kleisli[IO, Credentials, Either[String, Unit]] =
    Kleisli {
      case Credentials.Token(_, t) =>
        for {
          verified <- Jwt.verify(t)
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

  private val noAuthRequest: Kleisli[IO, Request[IO], Either[String, Unit]] =
    Kleisli(_ => IO(Right(())))

  private val onFailure: AuthedRoutes[String, IO] =
    Kleisli(err => {
      logger.debug(err.context)
      OptionT.liftF(Forbidden())
    })

  def noAuth(
    pf: PartialFunction[AuthedRequest[IO, Unit], IO[Response[IO]]]
  )(implicit F: Defer[IO],
    FA: Applicative[IO]
  ): HttpRoutes[IO] = {
    AuthMiddleware(noAuthRequest, onFailure).apply(AuthedRoutes.of(pf))
  }

  def apply(
    pf: PartialFunction[AuthedRequest[IO, Unit], IO[Response[IO]]]
  )(implicit F: Defer[IO],
    FA: Applicative[IO]
  ): HttpRoutes[IO] = {
    AuthMiddleware(authRequest, onFailure).apply(AuthedRoutes.of(pf))
  }
}

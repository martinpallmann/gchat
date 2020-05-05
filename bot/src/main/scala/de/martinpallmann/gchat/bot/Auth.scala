package de.martinpallmann.gchat.bot

import cats._
import cats.effect._
import cats.implicits._
import cats.data._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.headers.Authorization
import org.http4s.implicits._
import org.http4s.server._
import org.http4s.util.CaseInsensitiveString
import org.slf4j.LoggerFactory

object Auth {

  private val logger = LoggerFactory.getLogger(getClass)

  case class User(id: Long, name: String)

  def retrieveUser: Kleisli[IO, Credentials, User] =
    Kleisli {
      case Credentials.Token(s, t)      => IO(User(1L, s"$t"))
      case Credentials.AuthParams(_, _) => IO(User(1L, s"auth params"))
    }

  val authUser: Kleisli[IO, Request[IO], Either[String, User]] = Kleisli({
    request =>
      val message = for {
        header <- {
          request.headers
            .get(Authorization)
            .toRight(s"Couldn't find a valid Authorization header")
        }
      } yield header.credentials
      message.traverse(retrieveUser.run)
  })

  val authedRoutes: AuthedRoutes[User, IO] =
    AuthedRoutes.of {
      case GET -> Root as user => Ok(s"Welcome, ${user.name}")
    }

  val errRoutes: AuthedRoutes[String, IO] =
    AuthedRoutes.of {
      case _ as err =>
        logger.debug(err)
        //Unauthorized("") // TODO wtf
        Ok("unauthorized")
    }

  val middleware: AuthMiddleware[IO, User] =
    AuthMiddleware(authUser, errRoutes)

  val service: HttpRoutes[IO] = middleware(authedRoutes)

}

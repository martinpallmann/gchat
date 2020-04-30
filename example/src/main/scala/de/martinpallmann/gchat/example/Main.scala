package de.martinpallmann.gchat.example

import cats.effect.{ExitCode, IO, IOApp}
import de.martinpallmann.gchat.BotRequest.{
  AddedToSpace,
  MessageReceived,
  RemovedFromSpace
}
import de.martinpallmann.gchat.BotResponse.{
  Card,
  CardNoTitle,
  Empty,
  Text,
  TextCard
}
import de.martinpallmann.gchat.example.jwt.Verify
import de.martinpallmann.gchat.{BotRequest, BotResponse}
import de.martinpallmann.gchat.circe._
import de.martinpallmann.gchat.example.http.ErrorHandler
import de.martinpallmann.gchat.example.sys.Env
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.implicits._
import cats.implicits._
import de.martinpallmann.gchat.gen.Message

object Main extends IOApp {

  def routes: HttpRoutes[IO] = {

    val dsl = new Http4sDsl[IO] {}
    import dsl._

    HttpRoutes.of[IO] {
      case _ @GET -> Root => Ok("Hello World\n")
      case req @ POST -> Root =>
        for {
          evt <- req.as[BotRequest]
          resp <- Ok(eventHandling(evt).toMessage)
        } yield (resp)
    }
  }

  def arg(m: Message, s: String): Boolean =
    m.argumentText.map(_.trim).contains(s)

  val eventHandling: BotRequest => BotResponse = {
    case AddedToSpace(eventTime, space, message, user) =>
      Text(s"""Thanks for adding me to a space.
         |EventTime: $eventTime
         |Space: $space
         |Message: $message
         |User: $user
         |""".stripMargin)
    case MessageReceived(t, s, m, u) if arg(m, "debug") =>
      Text(s"""
              |`Hi ${u.displayName.getOrElse("stranger")}`
              |*You* sent _me_ a ~pidgeon~ message.
              |```
              |EventTime($t)
              |
              |$s
              |
              |$m
              |
              |$u
              |```""".stripMargin)
    case MessageReceived(t, s, m, u) if arg(m, "hey") =>
      Card("Hey", "Wicky Hey")
    case MessageReceived(t, s, m, u) =>
      TextCard("You said <b>" + m.argumentText.getOrElse("what?") + "</b>")
    case RemovedFromSpace(_, _, _) =>
      Empty
  }

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .withBanner(Nil)
      .bindHttp(Env.withDefault[Int]("PORT", 9000)(_.toIntOption), "0.0.0.0")
      .withHttpApp(routes.orNotFound)
      .withServiceErrorHandler(ErrorHandler[IO])
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}

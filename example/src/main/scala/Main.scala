import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import cats.implicits._
import de.martinpallmann.gchat.{BotRequest, BotResponse}
import de.martinpallmann.gchat.BotRequest.{
  AddedToSpace,
  MessageReceived,
  RemovedFromSpace
}
import de.martinpallmann.gchat.BotResponse._
import de.martinpallmann.gchat.circe._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._

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

  val eventHandling: BotRequest => BotResponse = {
    case AddedToSpace(eventTime, space, message, user) =>
      Text(s"""Thanks for adding me to a space.
         |EventTime: $eventTime
         |Space: $space
         |Message: $message
         |User: $user
         |""".stripMargin)
    case MessageReceived(eventTime, space, message, user) =>
      Text(s"""You sent a message.
         |EventTime: $eventTime
         |Space: $space
         |Message: $message
         |User: $user
         |""".stripMargin)
    case RemovedFromSpace(_, _, _) =>
      Empty
  }

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .withBanner(Nil)
      .bindHttp(sys.env.getOrElse("PORT", "9000").toInt, "0.0.0.0")
      .withHttpApp(routes.orNotFound)
      .withServiceErrorHandler(ErrorHandler[IO])
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}

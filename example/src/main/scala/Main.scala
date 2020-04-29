import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import cats.implicits._
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
          evt <- req.as[Event]
          resp <- Ok(TextResponse(eventHandling(evt)))
        } yield (resp)
    }
  }

  def eventHandling(evt: Event): String =
    (evt.`type`, evt.space, evt.message) match {
      case ("ADDED_TO_SPACE", Some(s), _) if s.`type` == "ROOM" =>
        s"Thanks for adding me to ${s.displayName}."
      case ("MESSAGE", _, Some(m)) => s"you sent a message. '${m.text}'"
      case _                       => "I don't understand."
    }

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .withBanner(Nil)
      .bindHttp(sys.env.getOrElse("PORT", "9000").toInt, "0.0.0.0")
      .withHttpApp(routes.orNotFound)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import cats.implicits._

object Main extends IOApp {

  def routes: HttpRoutes[IO] = {

    val dsl = new Http4sDsl[IO] {}
    import dsl._

    HttpRoutes.of[IO] {
      case _ @GET -> Root => Ok("Hello World\n")
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .withBanner(Nil)
      .bindHttp(9000, "0.0.0.0")
      .withHttpApp(routes.orNotFound)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}

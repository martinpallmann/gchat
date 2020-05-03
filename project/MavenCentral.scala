import java.net.URL
import java.io.{BufferedReader, InputStreamReader}
import io.circe.parser._

object MavenCentral {
  def version: String = {
    val url = new URL(
      "https://search.maven.org/solrsearch/select?q=de.martinpallmann.gchat&rows=1"
    )
    val in = new BufferedReader(new InputStreamReader(url.openStream))

    @scala.annotation.tailrec
    def read(acc: String = ""): String =
      in.readLine match {
        case null => acc
        case s    => read(s"$acc\n$s")
      }
    val result = read()
    in.close()
    extractVersion(result).getOrElse("x.x.x")
  }

  private def extractVersion(s: String): Option[String] =
    for {
      json <- parse(s).toOption
      res <- {
        val cursor = json.hcursor
        cursor
          .downField("response")
          .downField("docs")
          .downArray
          .downField("latestVersion")
          .as[String]
          .toOption
      }
    } yield res
}

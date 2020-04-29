import cats.effect.IO
import io.circe.{Decoder, Encoder}
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.jsonOf
import io.circe.generic.semiauto._

case class TextResponse(text: String)

object TextResponse {
  implicit val encoder: Encoder[TextResponse] = deriveEncoder
}

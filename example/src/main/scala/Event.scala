import Event.Msg
import cats.effect._
import io.circe.Decoder
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe._
import io.circe.generic.semiauto._

case class Event(`type`: String,
                 space: Option[Event.Space],
                 message: Option[Event.Msg])

object Event {
  implicit val decoder: Decoder[Event] = deriveDecoder

  case class Space(displayName: String, `type`: String)

  object Space {
    implicit val decoder: Decoder[Space] = deriveDecoder
  }

  case class Msg(text: String)

  object Msg {
    implicit val decoder: Decoder[Msg] = deriveDecoder
  }
}

import io.circe.{Decoder, KeyDecoder, KeyEncoder}
import PropertyName._

case class PropertyName(name: String) {
  override def toString: String =
    if (keywords.contains(name)) s"`$name`"
    else name
}

object PropertyName {

  def keywords = List("type")

  implicit val decoder: Decoder[PropertyName] =
    Decoder.decodeString.emap(s => Right(PropertyName(s)))

  implicit val keyDecoder: KeyDecoder[PropertyName] =
    new KeyDecoder[PropertyName] {
      def apply(key: String): Option[PropertyName] = Some(PropertyName(key))
    }
}

package de.martinpallmann.gchat.circe.event

import de.martinpallmann.gchat.event.Message
import io.circe.Decoder
import io.circe.generic.semiauto._

trait MessageDecoder
    extends InstantDecoder
    with UserDecoder
    with ThreadDecoder
    with AnnotationDecoder {
  implicit val decodeMessage: Decoder[Message] = deriveDecoder[Message]
}

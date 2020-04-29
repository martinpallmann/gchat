package de.martinpallmann.gchat.circe.event

import de.martinpallmann.gchat.event.Thread
import io.circe.Decoder
import io.circe.generic.semiauto._

trait ThreadDecoder {
  implicit val decodeThread: Decoder[Thread] = deriveDecoder[Thread]
}

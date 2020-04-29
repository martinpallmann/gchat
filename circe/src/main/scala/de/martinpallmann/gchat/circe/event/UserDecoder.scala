package de.martinpallmann.gchat.circe.event

import de.martinpallmann.gchat.event.User
import io.circe.Decoder
import io.circe.generic.semiauto._

trait UserDecoder {
  implicit val decodeUser: Decoder[User] = deriveDecoder[User]
}

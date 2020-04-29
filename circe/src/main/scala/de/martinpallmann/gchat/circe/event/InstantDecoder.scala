package de.martinpallmann.gchat.circe.event

import java.time.Instant

import io.circe.Decoder

import scala.util.Try

trait InstantDecoder {
  implicit val decodeInstant: Decoder[Instant] =
    Decoder.decodeString.emapTry(s => Try { Instant.parse(s) })
}

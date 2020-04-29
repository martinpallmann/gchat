package de.martinpallmann.gchat.circe

import de.martinpallmann.gchat.Event
import de.martinpallmann.gchat.tck.Tck
import io.circe.Json
import io.circe.parser._

import scala.util.Try

class IntegrationTest extends Tck[Json] with EventDecoder {

  def parseJson: String => Try[Json] = parse(_).toTry

  def decode: Json => Try[Event] = _.as[Event](decodeEvent).toTry
}

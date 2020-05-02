package de.martinpallmann.gchat.circe

import de.martinpallmann.gchat.BotRequest
import de.martinpallmann.gchat.tck.Tck
import io.circe.Json
import io.circe.parser._

import scala.util.Try

class IntegrationTest extends Tck[Json] with BotRequestDecoder {

  def parseJson: String => Try[Json] = parse(_).toTry

  def decode: Json => Try[BotRequest] = _.as[BotRequest](decodeBotRequest).toTry
}

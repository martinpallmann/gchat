package de.martinpallmann.gchat.circe

import de.martinpallmann.gchat.BotRequest
import de.martinpallmann.gchat.gen.Message
import de.martinpallmann.gchat.tck.Tck
import io.circe.Json
import io.circe.parser._

import scala.util.Try

class IntegrationTest
    extends Tck[Json]
    with BotRequestDecoder
    with MessageEncoder {

  def parseJson: String => Try[Json] = parse(_).toTry

  def decode: Json => Try[BotRequest] = _.as[BotRequest](decodeBotRequest).toTry

  def encode: Message => Json = encodeMessage.apply
}

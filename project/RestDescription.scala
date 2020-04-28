import java.io.File

import io.circe.Json
import io.circe.parser.parse
import io.circe._, io.circe.generic.semiauto._

import scala.io.Source

case class RestDescription(schemas: Map[String, Schema])

object RestDescription {

  implicit val decoder: Decoder[RestDescription] = deriveDecoder

  def fromJson(json: Json): RestDescription =
    json.as[RestDescription].toTry.get

  def fromFile(file: File): RestDescription =
    fromJson(parse(Source.fromFile(file).mkString).toTry.get)
}

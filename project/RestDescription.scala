import java.io.File

import io.circe.Json
import io.circe.parser.parse

import scala.io.Source

class RestDescription {}

object RestDescription {

  def fromJson(json: Json): RestDescription = ???

  def fromFile(file: File): RestDescription =
    fromJson(parse(Source.fromFile(file).mkString).toTry.get)
}

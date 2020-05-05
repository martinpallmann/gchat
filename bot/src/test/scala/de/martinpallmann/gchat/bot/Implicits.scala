package de.martinpallmann.gchat.bot

import io.circe.Json
import io.circe.parser.parse

object Implicits {

  implicit class JsonContext(val sc: StringContext) extends AnyVal {
    def json(args: Any*): Json = parse(sc.s(args: _*)).toTry.get
  }
}

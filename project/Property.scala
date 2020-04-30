import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class Property(`$ref`: Option[String],
                    `type`: Option[Property.Type],
                    items: Option[Property],
                    description: Option[String],
                    format: Option[String],
                    enum: Option[List[String]],
                    enumDescriptions: Option[List[String]]) {

  def enums(name: String): Option[Property.Enum] =
    for {
      en <- enum
      ed <- enumDescriptions
    } yield {
      Property.Enum(
        name,
        en.zip(ed)
          .filterNot {
            case (_, d) => d.contains("DO NOT USE")
          }
          .map {
            case (n, d) => Property.EnumValue(n, d)
          },
        None
      )
    }

  def show(name: String): String =
    (`$ref`, `type`, format, items, enums(name)) match {
      case (Some(s), _, _, _, _) => s
      case (None, Some(Property.Type.Array), _, Some(i), _) =>
        s"""Array[${i.show(name)}]"""
      case (None, Some(Property.Type.String), _, _, Some(_)) => name.capitalize
      case (None, Some(Property.Type.String), Some("google-datetime"), _, _) =>
        "java.time.Instant"
      case (None, Some(Property.Type.Integer), Some("int32"), _, _) =>
        "Int"
      case (None, Some(Property.Type.Number), Some("double"), _, _) =>
        "Double"
      case (None, Some(t), Some(x), _, _) => s"$t $x"
      case (None, Some(t), _, _, _)       => s"$t"
      case (_, _, _, _, _)                => throw new IllegalArgumentException("wtf")
    }
}

object Property {

  case class Enum(name: String, value: List[EnumValue], pckg: Option[String]) {

    def camelCase(s: String): String =
      s.foldLeft(("", true)) {
          case ((acc, _), '_')   => (acc, true)
          case ((acc, false), c) => (acc + c.toLower, false)
          case ((acc, true), c)  => (acc + c.toUpper, false)
        }
        ._1

    def values: String =
      value
        .map(ev => {
          val description = {
            if (ev.description.isEmpty) None
            else Some(s"/** ${ev.description} */\n  ")
          }.getOrElse("")
          s"  ${description}case object ${camelCase(ev.value)} extends $name"
        })
        .mkString("\n")

    def fileName: String =
      pckg.fold("") { p =>
        p.replace('.', '/') + "/"
      } + name + ".scala"

    def pckString: String = pckg.fold("")(p => s"package $p\n\n")

    def sourcecode: String =
      s"""${pckString}sealed trait $name
         |
         |object $name {
         |$values
         |}
         |""".stripMargin
  }
  case class EnumValue(value: String, description: String)

  sealed trait Type
  object Type {
    case object Boolean extends Type
    case object Array extends Type
    case object Integer extends Type
    case object String extends Type
    case object Number extends Type
    implicit val decoder: Decoder[Type] = Decoder.decodeString.emap {
      case "array"   => Right(Array)
      case "boolean" => Right(Boolean)
      case "integer" => Right(Integer)
      case "number"  => Right(Number)
      case "string"  => Right(String)
      case s         => Left(s"unkown property type: '$s'")
    }
  }

  implicit val decoder: Decoder[Property] = deriveDecoder
}

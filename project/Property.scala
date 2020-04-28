import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class Property(`$ref`: Option[String],
                    `type`: Option[Property.Type],
                    items: Option[Property],
                    description: Option[String]) {
  override def toString: String = (`$ref`, `type`, items) match {
    case (Some(s), _, _)                            => s
    case (None, Some(Property.Type.Array), Some(i)) => s"""Array[$i]"""
    case (None, Some(t), _)                         => s"$t"
    case (_, _, _)                                  => throw new IllegalArgumentException("wtf")
  }
}

object Property {

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

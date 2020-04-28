import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class Schema(id: String,
                  description: Option[String],
                  `type`: Schema.Type,
                  properties: Map[PropertyName, Property]) {

  private def safeComment(c: String) =
    c.replace("/", "&#47;")

  def propertiesScalaDoc: String =
    if (properties.isEmpty) ""
    else
      properties
        .flatMap {
          case (name, property) =>
            property.description.map(s => s"@param $name ${safeComment(s)}")
        }
        .mkString("\n\n", "\n", "")

  def beautify(s: String): String =
    if (s.isEmpty) ""
    else " * " + s.replace("\n", "\n * ")

  def classScalaDoc: String = description.fold("")(s => s"""/**
       |${beautify(s)}${beautify(propertiesScalaDoc)}
       | */
       |""".stripMargin)

  def vals: String =
    properties
      .map { case (name, property) => s"""$name: $property""" }
      .mkString(", ")

  def sourceCode: String =
    s"""
       |${classScalaDoc}case class $id($vals)
       |""".stripMargin
}

object Schema {

  sealed trait Type
  object Type {
    case object Object extends Type
    implicit val decoder: Decoder[Type] = Decoder.decodeString.emap {
      case "object" => Right(Object)
      case s        => Left(s"Unkown schema type '$s'")
    }
  }

  implicit val decoder: Decoder[Schema] = deriveDecoder
}

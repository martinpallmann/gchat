import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

import scala.collection.immutable

case class Schema(
  id: String,
  description: Option[String],
  `type`: Schema.Type,
  properties: Map[PropertyName, Property],
  pck: Option[String]) {

  def classes: List[ClassEtAlDef] =
    CaseClassDef(
      id,
      description.map(ScalaDoc),
      members,
      methods
    ) :: properties.toList
      .flatMap {
        case (name, property) =>
          property.enums2(id + name.name.capitalize)
      }

  def members: List[MemberDef] =
    properties.map {
      case (name, property) =>
        MemberDef(name.name, property.show(id + name.name.capitalize))
    }.toList

  def methods: List[MethodDef] = Nil
//    properties.map {
//      case (name, property) =>
//        MethodDef(
//          s"$name",
//          property.show(id + name.name.capitalize),
//          id,
//          s"this.copy($name = a)"
//        )
//    }.toList

  def pckString: String = pck.map(p => s"package $p\n\n").getOrElse("")

  def fileName: String =
    pck.map(_.replace('.', '/') + "/").getOrElse("") + s"$id.scala"

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
    else "\n * " + s.replace("\n", "\n * ")

  def classScalaDoc: String =
    description.fold("")(s => s"""/**${beautify(s.trim + propertiesScalaDoc)}
       | */
       |""".stripMargin)

  def vals: String =
    properties
      .map {
        case (name, property) =>
          s"""$name: ${property.show(id + name.name.capitalize)}"""
      }
      .mkString("\n  ", ",\n  ", "\n")

  def enums: List[Property.Enum] =
    properties.toList
      .flatMap {
        case (name, property) =>
          property.enums(id + name.name.capitalize)
      }

  def sourceCode: String =
    s"$pckString${classScalaDoc}case class $id($vals)\n".stripMargin
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

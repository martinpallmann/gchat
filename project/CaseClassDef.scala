sealed trait ClassEtAlDef {
  def src: String
}

case class CaseClassDef(
  name: String,
  doc: Option[ScalaDoc],
  members: List[MemberDef])
    extends ClassEtAlDef {

  private def srcMembers: String =
    if (members.isEmpty) ""
    else
      members.map(m => s"${m.name}: ${m.value}").mkString("\n  ", ",\n  ", "")

  def docStr: String = doc.fold("")(d => s"$d")

  def src: String =
    s"${docStr}final case class $name($srcMembers)"
}

case class EnumDef(name: String, values: List[String]) extends ClassEtAlDef {

  private def srcValues(indent: Int): String =
    values
      .map(v => " ".repeat(indent) + s"case object $v extends $name")
      .mkString("\n")

  def src: String =
    s"""|sealed trait $name
        |object $name {
        |${srcValues(2)}
        |}""".stripMargin
}

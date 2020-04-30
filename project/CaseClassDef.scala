sealed trait ClassEtAlDef {
  def src: String
}

case class CaseClassDef(name: String, members: List[MemberDef])
    extends ClassEtAlDef {

  private def srcMembers: String =
    if (members.isEmpty) ""
    else
      members.map(m => s"${m.name}: ${m.value}").mkString("\n  ", ",\n  ", "")

  def src: String =
    s"final case class $name($srcMembers)"
}

case class EnumDef(name: String, values: List[String]) extends ClassEtAlDef {

  private def srcValues: String =
    values.map(v => s"case object $v extends $name").mkString("\n")

  def src: String =
    s"""|sealed trait $name
        |object $name {
        |$srcValues
        |}""".stripMargin
}

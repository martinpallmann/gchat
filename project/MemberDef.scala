case class MemberDef(unsafeName: String, value: String) {
  def name: String =
    if (List("type").contains(unsafeName)) s"`$unsafeName`"
    else unsafeName
}

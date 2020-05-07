case class ScalaDoc(s: String) {
  override def toString: String =
    s.split('\n').mkString("/**\n  * ", "\n  * ", "\n  */\n")
}

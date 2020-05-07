case class MethodDef(
  name: String,
  inType: String,
  returnType: String,
  impl: String) {

  override def toString: String =
    s"def $name(a: $inType): $returnType = $impl"
}

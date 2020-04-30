import java.io.File

import sbt._
case class SourceFile(
  name: String,
  pckg: PackageDef,
  classes: List[ClassEtAlDef]) {

  private def classesSrc: String =
    classes.map(_.src).mkString("\n\n")

  def src(targetDir: File)(f: (File, Array[Byte]) => Unit): File = {
    val file = targetDir / s"${pckg.file}" / s"$name.scala"
    f(file, s"${pckg.src}${classesSrc}".getBytes("utf-8"))
    file
  }
}

import sbt._
import sbt.io.IO
import sbt.io.syntax.File

object SourceGen {

  val packageName: String = "de.martinpallmann.gchat"

  def generate(input: File, target: File): Seq[File] = {
    RestDescription
      .fromFile(input)
      .schemas
      .flatMap {
        case (_, schema) =>
          val s = schema.copy(pck = Some(packageName))
          val enums =
            s.enums
              .map(_.copy(pckg = Some(packageName)))
              .filterNot(_.name.contains("Deprecated"))
              .map(e => {
                val f = target / e.fileName
                IO.write(f, e.sourcecode)
                f
              })
          if (!s.id.contains("Deprecated")) {
            val file = target / s.fileName
            IO.write(file, s.sourceCode)
            file :: enums
          } else {
            enums
          }
      }
      .toSeq
  }
}

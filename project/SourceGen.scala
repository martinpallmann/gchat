import sbt._
import sbt.io.IO
import sbt.io.syntax.File

object SourceGen {
  def generate(input: File, target: File): Seq[File] = {
    RestDescription
      .fromFile(input)
      .schemas
      .map {
        case (_, schema) =>
          val file = target / s"${schema.id}.scala"
          IO.write(file, schema.sourceCode)
          file
      }
      .toSeq
  }
}

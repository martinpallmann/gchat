import sbt.io.IO
import sbt.io.syntax.File

object SourceGen {

  def sourceFile(s: Schema): SourceFile =
    SourceFile(s.id, PackageDef("de.martinpallmann.gchat.gen"), s.classes)

  def generate(input: File, target: File): Seq[File] = {
    RestDescription
      .fromFile(input)
      .schemas
      .values
      .toSeq
      .filterNot(_.id.contains("Deprecated"))
      .map(sourceFile(_).src(target)(IO.write))
  }
}

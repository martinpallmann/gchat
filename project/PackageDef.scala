import java.nio.file.{Path, Paths}

class PackageDef(pckgs: List[String]) {

  def add(s: String*): PackageDef =
    new PackageDef(pckgs ++ s.toList)

  def src: String =
    if (pckgs.isEmpty) ""
    else pckgs.mkString("package ", ".", "\n\n")

  def file: Path =
    if (pckgs.isEmpty) Paths.get(".")
    else Paths.get(pckgs.head, pckgs.tail: _*)
}

object PackageDef {
  def apply(ss: String*): PackageDef =
    new PackageDef(for {
      s <- ss.toList
      p <- s.split('.').toList
    } yield p)
}

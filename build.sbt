ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "de.martinpallmann"

lazy val core = project
  .settings(Compile / sourceGenerators += Def.task {
    val input = (Compile / sourceDirectory).value / "json" / "rest.json"
    val target = (Compile / sourceManaged).value
    SourceGen.generate(input, target)
  })

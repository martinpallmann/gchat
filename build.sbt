import sbt.Keys.mainClass

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "de.martinpallmann"
Global / onChangedBuildSource := ReloadOnSourceChanges

val http4sVersion = "0.21.1"

lazy val core = project
  .settings(Compile / sourceGenerators += Def.task {
    val input = (Compile / sourceDirectory).value / "json" / "rest.json"
    val target = (Compile / sourceManaged).value
    SourceGen.generate(input, target)
  })

lazy val example = project
  .dependsOn(core)
  .settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
    ),
    exportJars := true,
    mainClass in Compile := Some("Main")
  )
  .enablePlugins(JavaAppPackaging)

lazy val root = project
  .aggregate(core, example)
  .dependsOn(core, example)


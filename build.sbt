ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "de.martinpallmann.gchat"
Global / onChangedBuildSource := ReloadOnSourceChanges

enablePlugins(GitVersioning)

lazy val core = project
  .settings(Compile / sourceGenerators += Def.task {
    val input = (Compile / sourceDirectory).value / "json" / "rest.json"
    val target = (Compile / sourceManaged).value
    SourceGen.generate(input, target)
  })

lazy val tck = project
  .dependsOn(core)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % "3.1.1"
    )
  )

lazy val circe = project
  .dependsOn(core, tck % "test->compile")
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion
    ),
    publish / skip := true
  )

lazy val example = project
  .dependsOn(core, circe)
  .settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion
    ),
    exportJars := true,
    publish / skip := true,
    sbt.Keys.mainClass in Compile := Some(
      "de.martinpallmann.gchat.example.Main"
    )
  )
  .enablePlugins(JavaAppPackaging)

def circeVersion = "0.13.0"
def http4sVersion = "0.21.2"

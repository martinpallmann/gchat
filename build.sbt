import sbt.Keys.publishMavenStyle
import xerial.sbt.Sonatype.GitHubHosting

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "de.martinpallmann.gchat"
Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val commonSettings = Seq(
  headerLicense := Some(HeaderLicense.ALv2("2020", "Martin Pallmann")),
  publishTo := sonatypePublishToBundle.value,
  sonatypeProfileName := "de.martinpallmann.gchat",
  publishMavenStyle := true,
  licenses := Seq(
    "APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")
  ),
  sonatypeProjectHosting := Some(
    GitHubHosting("martinpallmann", "gchat", "sayhello@martinpallmann.de")
  )
)
publish / skip := true
enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

lazy val core = project
  .settings(
    moduleName := s"gchat-core",
    commonSettings,
    Compile / sourceGenerators += Def.task {
      val input = (Compile / sourceDirectory).value / "json" / "rest.json"
      val target = (Compile / sourceManaged).value
      SourceGen.generate(input, target)
    }
  )

lazy val tck = project
  .dependsOn(core)
  .settings(
    moduleName := s"gchat-tck",
    commonSettings,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % "3.1.1"
    ),
    publish / skip := true
  )

lazy val circe = project
  .dependsOn(core, tck % "test->compile")
  .settings(
    moduleName := s"gchat-circe",
    commonSettings,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion
    )
  )

lazy val example = project
  .dependsOn(core, circe)
  .settings(
    moduleName := s"gchat-example",
    commonSettings,
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

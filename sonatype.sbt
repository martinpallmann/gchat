import xerial.sbt.Sonatype._
sonatypeProfileName := "de.martinpallmann.gchat"
publishMavenStyle := true
// licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
sonatypeProjectHosting := Some(
  GitHubHosting("martinpallmann", "gchat", "sayhello@martinpallmann.de")
)
publishTo := sonatypePublishToBundle.value

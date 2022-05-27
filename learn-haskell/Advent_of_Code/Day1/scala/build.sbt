val catsEffectVersion = "3.3.0"

val catseffect = Seq(
  "org.typelevel" %% "cats-effect" % catsEffectVersion withSources () withJavadoc ()
)

val logging = Seq(
  "org.slf4j" % "slf4j-api" % "2.0.0-alpha4",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha10",
  "ch.qos.logback" % "logback-core" % "1.3.0-alpha10",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
)

val config = Seq("com.typesafe" % "config" % "1.4.1", "com.github.andr83" %% "scalaconfig" % "0.7")

val test = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10" withSources () withJavadoc (),
  "org.typelevel" %% "cats-effect-testing-scalatest" % "1.4.0" % Test withSources () withJavadoc (),
  "org.scalatest" %% "scalatest" % "3.2.10" % Test withSources () withJavadoc ()
)

lazy val day1 = (project in file("."))
  .settings(
    name := "common.Day1",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= logging,
    libraryDependencies ++= config,
    libraryDependencies ++= catseffect,
    libraryDependencies ++= test,
    scalaVersion := "2.13.6"
  )

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-explaintypes", // Explain type errors in more detail.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xsource:3", // Warn for Scala 3 features
  "-Ywarn-dead-code" // Warn when dead code is identified.
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.last
}

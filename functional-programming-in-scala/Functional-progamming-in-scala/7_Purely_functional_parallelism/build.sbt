ThisBuild / scalaVersion := "3.3.3"
ThisBuild / organization := "net.martinprobson.fpinscala.chap7"
ThisBuild / version := "0.0.1-SNAPSHOT"

lazy val par = (project in file("."))
  .settings(
      name := "Par",
      libraryDependencies ++=
        Seq(
          "ch.qos.logback" % "logback-classic" % "1.4.14",
          "ch.qos.logback" % "logback-core" % "1.4.14",
          "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
          "org.scalatest" %% "scalatest" % "3.2.18" % "test")
  )


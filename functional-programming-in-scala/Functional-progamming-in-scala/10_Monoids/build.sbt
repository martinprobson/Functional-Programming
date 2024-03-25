//
// Functional Programming in Scala - Chapter 10 - Monoids
//
//
scalacOptions ++= Seq( "-deprecation")

name := "Monoids"

version := "1.0"

scalaVersion := "3.3.3"

fork := true

resolvers += Resolver.mavenLocal

libraryDependencies += "net.martinprobson.fpinscala.chap7" %% "par" % "0.0.1-SNAPSHOT"

//libraryDependencies += "org.scalatestplus" %% "scalacheck-1-17" % "3.2.17.0" % "test"

//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-17" % "3.2.17.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18"

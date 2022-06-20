//
// Functional Programming in Scala - Chapter 10 - Monoids
//
//
scalacOptions ++= Seq(
	"-deprecation",
	"-Xcheckinit")

name := "Monoids"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" 

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" 

//
// Functional Programming in Scala - Chapter 8 - Property based testing
//
// Exercise 8.1 - Devise some property tests for: -
// sum: List[Int] => Int
//
name := "SumScalaCheck"

version := "1.0"

scalaVersion := "3.3.3"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"



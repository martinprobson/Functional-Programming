name := "State"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"
resolvers += Resolver.mavenLocal

libraryDependencies += "default" %% "funcstate" % "0.1-SNAPSHOT" 

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

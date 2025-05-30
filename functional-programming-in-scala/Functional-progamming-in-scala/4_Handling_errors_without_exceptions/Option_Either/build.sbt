name := "Option_Either"

version := "1.0"

scalaVersion := "2.12.2"
resolvers += Resolver.mavenLocal

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"

libraryDependencies += "default" %% "list" % "1.0"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25"

libraryDependencies += "org.clapper" %% "grizzled-slf4j" % "1.3.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

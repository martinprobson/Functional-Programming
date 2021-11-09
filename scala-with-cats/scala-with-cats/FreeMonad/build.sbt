

name := "FreeMonad"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.6"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  //"-Xfatal-warnings",     // make warnings errors
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint"               // enable handy linter warnings
)

libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"
libraryDependencies += "org.typelevel" %% "cats-free" % "2.6.1"

//addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

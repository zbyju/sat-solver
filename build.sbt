ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

lazy val root = (project in file("."))
  .settings(
    name := "SAT-solver"
  )

import Dependencies._

lazy val general = Seq(
  name := "not-poetry-bot",
  organization := "com.github.user",
  version := "1.0",
  scalaVersion := "2.12.1"
)

lazy val dependencies = Seq(
  configurator,
  scalaJHttp,
  gsonJson,
  openCsv
  //scalaXFiles
)

lazy val root = (project in file("."))
  .settings(
    general,
    libraryDependencies ++= dependencies,
    dependencyOverrides ++= overrides,
    assemblyJarName in assembly := name.value + "-" + version.value + ".jar"
  )  

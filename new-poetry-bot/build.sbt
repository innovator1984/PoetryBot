//name := "new-poetry-bot"
//organization := "io.github.username"
//version := "1.0"
//scalaVersion := "2.12.1"
//scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

//resolvers += Resolver.jcenterRepo

//libraryDependencies ++= {
//  val catsV          = "0.9.0"
//  val akkaHttpV      = "10.0.5"
//  val circeV         = "0.7.0"
//  val ficusV         = "1.4.0"
//  val scalaMockV     = "3.5.0"
//  val catsScalatestV = "2.2.0"

//  Seq(
//    "org.typelevel"     %% "cats-core"       % catsV,
//    "com.iheart"        %% "ficus"           % ficusV,
//    "com.typesafe.akka" %% "akka-http"       % akkaHttpV,
//    "de.heikoseeberger" %% "akka-http-circe" % "1.15.0",
//    "io.circe"          %% "circe-core"      % circeV,
//    "io.circe"          %% "circe-generic"   % circeV,
//    "io.circe"          %% "circe-parser"    % circeV,
//    "org.scalamock"     %% "scalamock-scalatest-support" % scalaMockV     % "it,test",
//    "com.ironcorelabs"  %% "cats-scalatest"              % catsScalatestV % "it,test",
//    "com.typesafe.akka" %% "akka-http-testkit"           % akkaHttpV      % "it,test"
//   )
// }

// lazy val root = project.in(file(".")).configs(IntegrationTest)
// Defaults.itSettings
// Revolver.settings
// enablePlugins(JavaAppPackaging)


// initialCommands := """
// import cats._
// import cats.data._
// import cats.implicits._
// import akka.actor._
// import akka.pattern._
// import akka.util._
// import scala.concurrent._
// import scala.concurrent.duration._
// """.stripMargin

import Dependencies._

lazy val general = Seq(
  name := "poetry-bot",
  organization := "io.github.username",
  version := "0.0.1",
  scalaVersion := "2.12.1"
)

lazy val dependencies = Seq(
  pack1,
  pack2,
  pack3,
  pack4,
  pack5,
  pack6,
  pack7,
  pack8 % "test",
  pack9 % "test",
  pack10 % "test",
  pack11,
  pack12,
  pack13,
  pack14
)

lazy val root = (project in file("."))
  .settings(
    general,
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
    resolvers ++= repos,
    libraryDependencies ++= dependencies,
    // dependencyOverrides ++= overrides,
    assemblyJarName in assembly := name.value + "-" + version.value + ".jar"
  )

//name := "poetry-bot"
//organization := "io.github.username"
//version := "0.0.1"
//scalaVersion := "2.12.1"
//scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")
//
//resolvers += Resolver.jcenterRepo
//
//libraryDependencies ++= {
//  val catsV          = "0.9.0"
//  val akkaHttpV      = "10.0.5"
//  val circeV         = "0.7.0"
//  val ficusV         = "1.4.0"
//  val scalaMockV     = "3.5.0"
//  val catsScalatestV = "2.2.0"
//  // EXTRA
//  val loggingV       = "3.7.2"
//  val telegramV      = "3.2"
//
//  Seq(
//    "org.typelevel"     %% "cats-core"       % catsV,
//    "com.iheart"        %% "ficus"           % ficusV,
//    "com.typesafe.akka" %% "akka-http"       % akkaHttpV,
//    "de.heikoseeberger" %% "akka-http-circe" % "1.15.0",
//    "io.circe"          %% "circe-core"      % circeV,
//    "io.circe"          %% "circe-generic"   % circeV,
//    "io.circe"          %% "circe-parser"    % circeV,
//    "org.scalamock"     %% "scalamock-scalatest-support" % scalaMockV     % "it,test",
//    "com.ironcorelabs"  %% "cats-scalatest"              % catsScalatestV % "it,test",
//    "com.typesafe.akka" %% "akka-http-testkit"           % akkaHttpV      % "it,test",
//    // EXTRA
//    "com.typesafe.scala-logging" %% "scala-logging"      % loggingV,
//    "org.telegram" % "telegrambots"                     % telegramV
//  )
//}
//
//lazy val root = project.in(file(".")).configs(IntegrationTest)
//Defaults.itSettings
//Revolver.settings
//enablePlugins(JavaAppPackaging)
//
//initialCommands := """
//import cats._
//import cats.data._
//import cats.implicits._
//import akka.actor._
//import akka.pattern._
//import akka.util._
//import scala.concurrent._
//import scala.concurrent.duration._
//""".stripMargin

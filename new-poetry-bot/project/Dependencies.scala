import sbt._

object Dependencies {
  lazy val catsV          = "0.9.0"
  lazy val akkaHttpV      = "10.0.5"
  lazy val circeV         = "0.7.0"
  lazy val ficusV         = "1.4.0"
  lazy val scalaMockV     = "3.5.0"
  lazy val catsScalatestV = "2.2.0"
  // EXTRA
  lazy val loggingV       = "3.7.2"
  lazy val telegramV      = "3.2"

  lazy val pack1       = "org.typelevel"     %% "cats-core"       % catsV
  lazy val pack2       = "com.iheart"        %% "ficus"           % ficusV
  lazy val pack3       = "com.typesafe.akka" %% "akka-http"       % akkaHttpV
  lazy val pack4       = "de.heikoseeberger" %% "akka-http-circe" % "1.15.0"
  lazy val pack5       = "io.circe"          %% "circe-core"      % circeV
  lazy val pack6       = "io.circe"          %% "circe-generic"   % circeV
  lazy val pack7       = "io.circe"          %% "circe-parser"    % circeV
  lazy val pack8       = "org.scalamock"     %% "scalamock-scalatest-support" % scalaMockV
  lazy val pack9       = "com.ironcorelabs"  %% "cats-scalatest"              % catsScalatestV
  lazy val pack10      = "com.typesafe.akka" %% "akka-http-testkit"           % akkaHttpV
  // EXTRA
  lazy val pack11      = "com.typesafe.scala-logging" %% "scala-logging"      % loggingV
  lazy val pack12      = "org.telegram" % "telegrambots"                     % telegramV

  lazy val pack13      = "com.typesafe" % "config" % "1.3.1"
  lazy val pack14      = "org.slf4j" % "slf4j-jdk14" % "1.7.25"

  lazy val repos = Seq(Resolver.jcenterRepo)

  lazy val overrides = Seq(
    "commons-net" % "commons-net" % "3.1",
    "com.google.guava" % "guava" % "14.0.1",
    "com.google.code.findbugs" % "jsr305" % "3.0.0",
    "io.netty" % "netty" % "3.8.0.Final",
    "io.netty" % "netty-all" % "4.0.29.Final"
  )
}

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
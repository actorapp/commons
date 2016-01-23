import sbt._
import Keys._

object V {
  val Akka = "2.4.1"
  val Scalatest = "2.2.4"
  val Slick = "3.1.1"
  val Cats = "0.3.0"
}

object Dependencies {
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % V.Akka % "provided"

  val scalatest = "org.scalatest" %% "scalatest" % V.Scalatest

  val slick = "com.typesafe.slick" %% "slick" % V.Slick % "provided"

  val cats = "org.spire-math" %% "cats" % V.Cats % "provided"
}

import sbt._
import Keys._

object V {
  val Akka = "2.3.13"
  val Scalatest = "2.2.4"
}

object Dependencies {
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % V.Akka

  val scalatest = "org.scalatest" %% "scalatest" % V.Scalatest
}
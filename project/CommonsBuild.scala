import sbt.Keys._
import sbt._

object CommonsBuild {
  val settings: Seq[Setting[_]] = Seq(
    organization := "im.actor",
    scalaVersion := "2.11.7",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases")
    )
  )
}

import sbt.Keys._
import sbtrelease._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys._

name := "actor-commons"

CommonsBuild.settings

lazy val actorConcurrent = project in file("actor-concurrent")
lazy val actorUtil = project in file("actor-util")
lazy val actorCatsSlick = project in file("actor-cats-slick")
lazy val actorStorage = project in file("actor-storage")
lazy val actorStorageSlick = project in file("actor-storage-slick") dependsOn actorStorage

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _)),
  setNextVersion,
  commitNextVersion,
  releaseStepTask(publishSigned in actorConcurrent),
  releaseStepTask(publishSigned in actorUtil),
  releaseStepTask(publishSigned in actorCatsSlick),
  releaseStepTask(publishSigned in actorStorage),
  releaseStepTask(publishSigned in actorStorageSlick),
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
  pushChanges
)

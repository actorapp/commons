import sbt.Keys._
import sbtrelease._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys._

name := "actor-commons"

CommonsBuild.settings

lazy val actorConcurrent = project in file("actor-concurrent")

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
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
  pushChanges
)

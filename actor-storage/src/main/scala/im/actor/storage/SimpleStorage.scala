package im.actor.storage

import scala.concurrent.Future

trait Connector {
  def run[R](action: api.Action[R]): Future[R]

  def createSync(name: String): Unit

  def create(name: String): Future[Unit]
}

class SimpleStorage(val name: String) {
  import api._

  final def get(key: String) = GetAction(name, key)

  final def getByPrefix(keyPrefix: String) = GetByPrefix(name, keyPrefix)

  final def put(key: String, value: Array[Byte]) = PutAction(name, key, value)

  final def delete(key: String) = DeleteAction(name, key)

  final def getKeys = GetKeys(name)
}

object api {
  sealed trait Action[R] {
    def name: String
  }

  final case class GetAction(name: String, key: String) extends Action[Option[Array[Byte]]]

  final case class GetByPrefix(name: String, keyPrefix: String) extends Action[Vector[(String, Array[Byte])]]

  final case class PutAction(name: String, key: String, value: Array[Byte]) extends Action[Int]

  final case class DeleteAction(name: String, key: String) extends Action[Int]

  final case class GetKeys(name: String) extends Action[Seq[String]]

}

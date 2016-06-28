package im.actor.storage.slick

import java.util.concurrent.ConcurrentHashMap

import com.github.tminglei.slickpg._
import im.actor.storage.Connector
import im.actor.storage.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

private object Driver extends ExPostgresDriver
import im.actor.storage.slick.Driver.api._

class SlickConnector(db: Database)(implicit ec: ExecutionContext) extends Connector {

  private val tables = new ConcurrentHashMap[String, Unit]()

  override def createSync(name: String): Unit = synchronized {
    if(tables.containsKey(name)) {
      ()
    } else {
      Await.result(create(name), 10 seconds)
      tables.put(name, ())
    }
  }

  override def create(name: String): Future[Unit] =
    db.run(sqlu"""CREATE TABLE IF NOT EXISTS #${tableName(name)} (key TEXT, value BYTEA, PRIMARY KEY (key))""") map (_ => ())

  override def run[R](action: Action[R]): Future[R] = {
    createSync(action.name) // create if not exists
    for {
      result <- (action match {
        case GetAction(name, key) => get(name, key)
        case GetByPrefix(name, key) => getByPrefix(name, key)
        case PutAction(name, key, value) => put(name, key, value)
        case DeleteAction(name, key) => delete(name, key)
        case GetKeys(name) => getKeys(name)
      }).asInstanceOf[Future[R]]
    } yield result
  }

  private def tableName(name: String) = s"kv_$name"

  private def get(name: String, key: String): Future[Option[Array[Byte]]] =
    db.run(sql"""SELECT value FROM #${tableName(name)} WHERE key = $key""".as[Array[Byte]].headOption)

  private def getByPrefix(name: String, keyPrefix: String): Future[Vector[(String, Array[Byte])]] =
    db.run(sql"""SELECT (key, value) FROM #${tableName(name)} WHERE key like $keyPrefix%""".as[(String, Array[Byte])])

  private def put(name: String, key: String, value: Array[Byte]): Future[Int] =
    db.run(sqlu"""INSERT INTO #${tableName(name)} VALUES ($key, $value)""")

  private def delete(name: String, key: String) =
    db.run(sqlu"""DELETE FROM #${tableName(name)} WHERE key = $key""")

  private def getKeys(name: String) =
    db.run(sql"""SELECT key FROM #${tableName(name)}""".as[String])
}

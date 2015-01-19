package de.choffmeister.akkavsnode.models

import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.api.indexes._
import reactivemongo.bson._

import scala.concurrent._

case class User(
  id: BSONObjectID = BSONObjectID("00" * 12),
  name: String,
  description: String) extends BaseModel

class UserTable(database: Database, collection: BSONCollection)(implicit executor: ExecutionContext) extends Table[User](database, collection) {
  implicit val reader = UserBSONFormat.Reader
  implicit val writer = UserBSONFormat.Writer
}

object UserBSONFormat {
  implicit object Reader extends BSONDocumentReader[User] {
    def read(doc: BSONDocument): User = User(
      id = doc.getAs[BSONObjectID]("_id").get,
      name = doc.getAs[String]("name").get,
      description = doc.getAs[String]("description").get
    )
  }

  implicit object Writer extends BSONDocumentWriter[User] {
    def write(obj: User): BSONDocument = BSONDocument(
      "_id" -> obj.id,
      "name" -> obj.name,
      "description" -> obj.description
    )
  }
}

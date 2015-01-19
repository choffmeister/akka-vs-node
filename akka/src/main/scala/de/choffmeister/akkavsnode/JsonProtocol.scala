package de.choffmeister.akkavsnode

import akka.http.marshallers.sprayjson.SprayJsonSupport
import de.choffmeister.akkavsnode.models._
import reactivemongo.bson._
import spray.json._

trait BSONJsonProtocol extends DefaultJsonProtocol {
  implicit object BSONObjectIDFormat extends JsonFormat[BSONObjectID] {
    def write(id: BSONObjectID): JsValue = JsString(id.stringify)
    def read(value: JsValue): BSONObjectID =
      value match {
        case JsString(str) => BSONObjectID(str)
        case _ => deserializationError("BSON ID expected: " + value)
      }
  }
}

trait JsonProtocol extends DefaultJsonProtocol
    with BSONJsonProtocol
    with SprayJsonSupport {
  implicit val userFormat = jsonFormat3(User)
}

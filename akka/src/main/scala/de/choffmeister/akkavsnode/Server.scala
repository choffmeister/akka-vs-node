package de.choffmeister.akkavsnode

import akka.actor._
import akka.http.Http
import akka.http.server.Directives._
import akka.stream.FlowMaterializer
import de.choffmeister.akkavsnode.models._
import reactivemongo.bson.BSONDocument

import scala.concurrent.duration._
import scala.util.Random

class Server extends Bootable with JsonProtocol {
  implicit val system = ActorSystem("akkavsnode")
  implicit val executor = system.dispatcher
  implicit val materializer = FlowMaterializer()
  lazy val database = Database.open(("localhost", 27017) :: Nil, "akkavsnode")
  lazy val random = new Random()

  def startup(): Unit = {
    val binding = Http(system).bind(interface = "0.0.0.0", 8080)
    val routes = path("api" / "user") {
      complete(database.users.queryOne(BSONDocument("name" -> s"user-${random.nextInt(10000) + 1}")))
    }

    binding.startHandlingWith(routes)
  }

  def shutdown(): Unit = {
    system.shutdown()
    system.awaitTermination(1.seconds)
  }
}

object Server extends BootableApp[Server]

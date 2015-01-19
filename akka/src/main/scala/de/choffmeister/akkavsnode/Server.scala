package de.choffmeister.akkavsnode

import akka.actor._
import akka.http.Http
import akka.http.server.Directives._
import akka.stream.FlowMaterializer
import de.choffmeister.akkavsnode.models._

import scala.concurrent.duration._

class Server extends Bootable with JsonProtocol {
  implicit val system = ActorSystem("akkavsnode")
  implicit val executor = system.dispatcher
  implicit val materializer = FlowMaterializer()
  lazy val database = Database.open(("localhost", 27017) :: Nil, "akkavsnode")

  def startup(): Unit = {
    val binding = Http(system).bind(interface = "0.0.0.0", 8080)
    val routes = path("api" / "user") {
      complete(User(name = "tom", age = 23))
    }

    binding.startHandlingWith(routes)
  }

  def shutdown(): Unit = {
    system.shutdown()
    system.awaitTermination(1.seconds)
  }
}

object Server extends BootableApp[Server]

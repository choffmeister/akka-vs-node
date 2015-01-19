package de.choffmeister.akkavsnode

import scala.reflect.Manifest

trait Bootable {
  def startup(): Unit
  def shutdown(): Unit
}

class BootableApp[T <: Bootable: Manifest] extends App {
  val manifest = implicitly[Manifest[T]]
  val bootable = manifest.runtimeClass.newInstance.asInstanceOf[T]
  sys.ShutdownHookThread(bootable.shutdown())
  bootable.startup()
}

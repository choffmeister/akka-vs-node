name := "akkavsnode"

organization := "de.choffmeister.akkavsnode"

scalaVersion := "2.11.5"

scalacOptions ++= Seq("-encoding", "utf8")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.7",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-M2",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0-M2",
  "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23"
)

packSettings

packMain := Map("server" -> "de.choffmeister.akkavsnode.Server")

resolvers += "Typesafe releases" at "http://repo.typesafe.com/typesafe/releases/"

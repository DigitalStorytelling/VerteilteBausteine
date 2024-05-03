package cluster

import akka.actor.typed.{ ActorSystem}

import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val port = args(0).toInt
    val role = args(1)
    val configuration = createConfiguration(port)


    ActorSystem[Nothing](Guardian(role), "ping", configuration)

    println("Press any key to stop")
    StdIn.readLine();

  }

  def createConfiguration(port: Int) : Config = ConfigFactory
    .parseString(s"akka.remote.artery.canonical.port = $port")
    .withFallback(ConfigFactory.load())
}

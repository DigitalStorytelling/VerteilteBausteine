package cluster

import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    /*val port = args(0).toInt
    val role = args(1)*/

    val port = System.getenv("port").toInt
    val role: String = System.getenv("role")

    // get hostname (docker)
    val configuration = createConfiguration(port, java.net.InetAddress.getLocalHost.getHostName)

    ActorSystem[Nothing](Guardian(role), "ping", configuration)

    println("PRESS ANY KEY TO END")
    StdIn.readLine()
    println("SHUTTING DOWN")

  }

  private def createConfiguration(port: Int, hostname: String): Config = ConfigFactory
    .parseString(s"akka.remote.artery.canonical.hostname = $hostname\nakka.remote.artery.canonical.port = $port")
    .withFallback(ConfigFactory.load())
}
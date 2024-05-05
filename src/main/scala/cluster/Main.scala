package cluster

import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {
    val role: String = System.getenv("role")

    // get hostname (docker)
    val configuration = createConfiguration(java.net.InetAddress.getLocalHost.getHostName)

    ActorSystem[Nothing](Guardian(role), "ping", configuration)

    println("PRESS ANY KEY TO END")
    StdIn.readLine()
    println("SHUTTING DOWN")

  }

  private def createConfiguration(hostname: String): Config = ConfigFactory
    .parseString(s"akka.remote.artery.canonical.hostname = $hostname")
    .withFallback(ConfigFactory.load())

}
package cluster

import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    /*val port = args(0).toInt
    val role = args(1)*/

    val userInput = System.getenv("envVar")
    val userInputArray = userInput.split(",")

    val port = userInputArray(0).toInt
    val role: String = userInputArray(1)
    val nameDockerContainer = userInputArray(2)

    val configuration = createConfiguration(port, nameDockerContainer)


    ActorSystem[Nothing](Guardian(role), "ping", configuration)


    while (true) {}
    //println("Press any key to exit")
    //StdIn.readLine()

  }

  private def createConfiguration(port: Int, hostname: String): Config = ConfigFactory
    .parseString(s"akka.remote.artery.canonical.hostname = $hostname\nakka.remote.artery.canonical.port = $port")
    .withFallback(ConfigFactory.load())
}
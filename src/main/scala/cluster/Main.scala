package cluster

import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    /*val port = args(0).toInt
    val role = args(1)*/

    println("PROGRAMS STARTS")

    val userInput = System.getenv("envVar")
    val userInputArray = userInput.split(",")

    val port = userInputArray(0).toInt
    val role: String = userInputArray(1)


    val configuration = createConfiguration(port)

    ActorSystem[Nothing](Guardian(role), "ping", configuration)


    println("Press any key to exit")
    StdIn.readLine()

  }

  private def createConfiguration(port: Int) : Config = ConfigFactory
    .parseString(s"akka.remote.artery.canonical.port = $port")
    .withFallback(ConfigFactory.load())
}

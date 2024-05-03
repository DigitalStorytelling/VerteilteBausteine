package Praktikum1
import io.circe.generic.auto._
import io.circe.parser._

import scala.io.Source
import scala.util.Using

object LoadJSON {

  def main(args: Array[String]): Unit = {

    val file = "orders.json/orders.json"

    Using(Source.fromFile(file)) { source =>
      for (line <- source.getLines) {
        val parseResult = decode[Order](line.dropRight(1))
        parseResult match {
          case Right(order) => println(order)
          case Left(error) => println(s"Failed to decode line: $line. Error: $error")
        }
      }
    }
  }
}

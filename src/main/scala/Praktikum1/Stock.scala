package Praktikum1

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Stock {

  def apply(): Behavior[CommandStock] = {
    Behaviors.setup { (context) =>
      context.log.info("Stock started")

      Behaviors.receiveMessage {
        case currentItems: GetItems =>
          // später folgt hier Implementierung
          Behaviors.same
      }
    }
  }

  sealed trait CommandStock

  case class GetItems(item: List[Item]) extends CommandStock
}

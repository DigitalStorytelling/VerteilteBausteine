package Praktikum1

import Praktikum1.Finance.{CommandFinance, GetSumTotalOrders, GetCustomerAndPrice}
import Praktikum1.Reader.{CommandReader, Next}
import Praktikum1.Stock.{CommandStock, GetItems}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object OrderDispatcher {
  def apply(actorReader: ActorRef[CommandReader], actorFinance: ActorRef[CommandFinance], actorStock: ActorRef[CommandStock]): Behavior[CommandOrderDispatcher] =
    Behaviors.setup { (context) =>
      context.log.info("Order Dispatcher started")
      actorReader ! Next(context.self)

      Behaviors.receiveMessage {
        case currentAnswer: NextOrder =>
          actorReader ! Next(context.self)

          //an Finanzen weitergeben wie teuer die Bestellung insgesamt ist
          val totalSum: Int = currentAnswer.order.items.map(item => item.price).sum
          actorFinance ! GetCustomerAndPrice(currentAnswer.order.customer, totalSum)

          // An Stock Item List weiterreichen
          actorStock ! GetItems(currentAnswer.order.items)

          Behaviors.same

        case Empty =>
          actorFinance ! GetSumTotalOrders(1000000)
          actorFinance ! GetSumTotalOrders(19448)
          Behaviors.stopped
      }
    }

  sealed trait CommandOrderDispatcher

  case class NextOrder(order: Order) extends CommandOrderDispatcher
  case object Empty extends CommandOrderDispatcher

}

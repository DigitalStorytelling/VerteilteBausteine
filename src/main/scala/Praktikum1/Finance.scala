package Praktikum1

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.collection.immutable.HashMap

object Finance {
  def apply(mapCustomer: HashMap[Int, Int] = new HashMap()): Behavior[CommandFinance] = {
    Behaviors.setup { (context) =>

      Behaviors.receiveMessage {
        case currentOrder: GetCustomerAndPrice => {

          val currentSum = mapCustomer.getOrElse(currentOrder.customer.id, 0)
          val updatedCustomer = mapCustomer + (currentOrder.customer.id -> (currentSum + currentOrder.totalPrice))

          apply(updatedCustomer)
        }
        case currentCustomer: GetSumTotalOrders => {

          val balance = mapCustomer.getOrElse(currentCustomer.customerId, "unkown")
          context.log.info(s"Balance of customer ${currentCustomer.customerId} is $balance")

          Behaviors.same
        }
      }
    }
  }

  sealed trait CommandFinance

  case class GetCustomerAndPrice(customer: Customer, totalPrice: Int) extends CommandFinance

  case class GetSumTotalOrders(customerId: Int) extends CommandFinance
}
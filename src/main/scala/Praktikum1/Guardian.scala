package Praktikum1
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Guardian {
  def apply(): Behavior[Nothing] = Behaviors.setup[Nothing] { context =>
    context.log.info("Guardian started")

    val actorReader = context.spawn(Reader(), "Reader")
    val actorFinance = context.spawn(Finance(), "Finance")
    val actorStock = context.spawn(Stock(), "Stock")

    context.spawn(OrderDispatcher(actorReader, actorFinance, actorStock), "Dispatcher")

    Behaviors.same
  }
}
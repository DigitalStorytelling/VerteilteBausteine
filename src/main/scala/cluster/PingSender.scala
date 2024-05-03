package cluster

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object PingSender {

  def apply(actorLogger: ActorRef[PingLogger.Ping]): Behavior[PingLogger.Ping.type ] = Behaviors.setup{ context =>
    context.log.info("Sender started")

    actorLogger ! PingLogger.Ping()

    Behaviors.empty
  }

  /*val PingLoggerKey: ServiceKey[PingLogger.Ping] = ServiceKey("ping-logger")

  def apply(): Behavior[Receptionist.Listing] = {
    Behaviors.setup { context =>
      context.system.receptionist ! Receptionist.Subscribe(PingLoggerKey, context.self)

      Behaviors.receiveMessage {
        case PingLoggerKey.Listing(listings) if listings.isEmpty =>
          context.log.info("There are no PingLogger instances available")
          Behaviors.same
        case PingLoggerKey.Listing(listings) =>
          listings.foreach(logger => context.spawnAnonymous(PingSender(logger)))
          Behaviors.same
      }
    }
  }

  def apply(actorLogger: ActorRef[PingLogger.Ping]): Behavior[PingLogger.Ping.type] = {
    Behaviors.setup { context =>
      context.log.info("Sender started")
      actorLogger ! PingLogger.Ping()
      Behaviors.empty
    }
  }*/

}

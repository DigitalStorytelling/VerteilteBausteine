package cluster

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object PingSender {

  def apply(actorLogger: ActorRef[PingLogger.Ping]): Behavior[PingLogger.Ping.type ] = Behaviors.setup{ context =>
    context.log.info("Sender started")

    actorLogger ! PingLogger.Ping()
    Behaviors.empty
  }
}
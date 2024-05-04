package cluster

import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.scaladsl.Behaviors

object Guardian {

  def apply(role: String): Behavior[Nothing] =
    Behaviors.setup[Receptionist.Listing] { context =>

      context.log.info("Guardian started")

      role match {
        case "-client" =>
          context.log.info("Client detected")
          context.system.receptionist ! Receptionist.Subscribe(PingLogger.PingLoggerKey, context.self)

        case "-server" =>
          context.log.info("Server detected")
          context.spawnAnonymous(PingLogger())

        case _ =>
          context.log.info("Unknown starting role")
      }

      Behaviors.receiveMessagePartial {
        case PingLogger.PingLoggerKey.Listing(listings) if listings.nonEmpty =>
          context.log.info("At least one PingLogger available")
          listings.foreach { ps => context.spawnAnonymous(PingSender(ps)) }
          Behaviors.same

        case PingLogger.PingLoggerKey.Listing(listings) if listings.isEmpty =>
          context.log.info("No PingLogger available")
          Behaviors.same
      }
    }.narrow
}

package cluster


import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors


object PingLogger {

  val PingLoggerKey: ServiceKey[Ping] = ServiceKey[Ping]("LoggerService")

  def apply(): Behavior[CommandLogger] = Behaviors.setup { (context) =>

    context.system.receptionist ! Receptionist.Register(PingLogger.PingLoggerKey, context.self)

    Behaviors.receiveMessage {
      case ping: Ping =>
        context.log.info("I was pinged...")
        Behaviors.same
    }
  }

  trait CommandLogger
  case class Ping() extends CommandLogger //with JsonSerializable
}

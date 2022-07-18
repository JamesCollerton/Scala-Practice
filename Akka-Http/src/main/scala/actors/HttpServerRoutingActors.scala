package org.example.application
package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, PostStop}
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.Http

import scala.concurrent.Future
import scala.util.{Failure, Success}

object HttpServerRoutingActors {

  /*
    Messages we want to use to tell us if the system has successfully started or not.
   */
  sealed trait Message
  private final case class StartFailed(cause: Throwable) extends Message
  private final case class Started(binding: ServerBinding) extends Message
  case object Stop extends Message

  /*
    Here we use the Behaviours.setup method as we would like this behaviour to begin immediately, not as a reaction
    to a message.
   */
  def apply(host: String, port: Int): Behavior[Message] = Behaviors.setup { ctx =>

    /*
      Extracting the system from the context so we can spawn new actors in order to submit jobs
     */
    implicit val system: ActorSystem[Nothing] = ctx.system

    /*
      Here we create the initial JobRepository actor, which contains a behaviour we can submit jobs to. Notice how it gives
      us an ActorRef, as we never speak directly to an Actor, only to a reference.
     */
    val buildJobRepository: ActorRef[JobRepository.Command] = ctx.spawn(JobRepository(), "JobRepository")

    /*
      Creates the routes we expect to use, later binding them to the server
     */
    val routes: JobRoutes = new JobRoutes(buildJobRepository)

    /*
      Finally we create the server and bind the routes to it.
     */
    val serverBinding: Future[Http.ServerBinding] =
      Http().newServerAt(host, port).bind(routes.theJobRoutes)

    /*
      When using an API that returns a Future from an actor it’s common that you would like to use the value of the
      response in the actor when the Future is completed. For this purpose the ActorContext provides a pipeToSelf method.

      Notice how the server binding extends the 'Try' trait, so has a success and failure route we can match on.

      These are messages passed to the actor when the server binding has finished. They are used in the later running and
      started methods
     */
    ctx.pipeToSelf(serverBinding) {
      case Success(binding) => Started(binding)
      case Failure(ex)      => StartFailed(ex)
    }

    def running(binding: ServerBinding): Behavior[Message] =
      Behaviors.receiveMessagePartial[Message] {
        case Stop =>
          ctx.log.info(
            "Stopping server http://{}:{}/",
            binding.localAddress.getHostString,
            binding.localAddress.getPort)
          Behaviors.stopped
      }.receiveSignal {
        case (_, PostStop) =>
          binding.unbind()
          Behaviors.same
      }

    /*
      Here we match the message piped to the context after the server started up.
     */
    def starting(wasStopped: Boolean): Behaviors.Receive[Message] =
      Behaviors.receiveMessage[Message] {
        case StartFailed(cause) =>
          throw new RuntimeException("Server failed to start", cause)
        case Started(binding) =>
          ctx.log.info(
            "Server online at http://{}:{}/",
            binding.localAddress.getHostString,
            binding.localAddress.getPort)
          if (wasStopped) ctx.self ! Stop
          running(binding)
        case Stop =>
          // we got a stop message but haven't completed starting yet,
          // we cannot stop until starting has completed
          starting(wasStopped = true)
      }

    starting(wasStopped = false)
  }

  def main(args: Array[String]): Unit = {
    /*
      This is the entryway to the application. We create an actor system in order to be able to submit jobs. The actor
      system is created using the start up behaviour supplied by HttpServerRoutingActors
     */
    val system: ActorSystem[HttpServerRoutingActors.Message] =
      ActorSystem(HttpServerRoutingActors("localhost", 8080), "BuildJobsServer")
  }
}

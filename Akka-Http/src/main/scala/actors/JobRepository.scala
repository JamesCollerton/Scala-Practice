package org.example.application
package actors

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

object JobRepository {

  /*
   Definition of all possible statuses of a job, it can either be a failure or a success. Technically this could
   be an enum
   */
  sealed trait Status
  object Successful extends Status
  object Failed extends Status

  /*
    This is the core job object, we will be submitting these to the job repository.
   */
  final case class Job(id: Long, projectName: String, status: Status, duration: Long)

  /*
   Trait defining successful and failure responses. We can either have the OK object, or an error object with a reason
   */
  sealed trait Response
  case object OK extends Response
  final case class KO(reason: String) extends Response

  /*
   Trait and its implementations representing all possible messages that can be sent to this Behavior. So for each command
   we supply a job and then an actor to respond to. Notice we always just take a reference to the actor so the actor
   itself can be stored anywhere
   */
  sealed trait Command
  final case class AddJob(job: Job, replyTo: ActorRef[Response]) extends Command
  final case class GetJobById(id: Long, replyTo: ActorRef[Option[Job]]) extends Command
  final case class ClearJobs(replyTo: ActorRef[Response]) extends Command

  /*
   This behavior handles all possible incoming messages and keeps the state in the function parameter

   The jobs variable is only handed to the function once, but only needs to be handed once as it is retained within
   the Behaviours object.
   */
  def apply(jobs: Map[Long, Job] = Map.empty): Behavior[Command] = Behaviors.receiveMessage {
    /*
      So here we say 'if the command message we've received is to add a job and we already have it in the map then
      we tell whoever we need to reply to that the Job already exists
     */
    case AddJob(job, replyTo) if jobs.contains(job.id) =>
      // Remember that the exclamation mark is just shorthand for 'tell'
      replyTo ! KO("Job already exists")
      // The reason we return the same behaviour is because we want the same behaviour with the existing map
      Behaviors.same
    case AddJob(job, replyTo) =>
      replyTo ! OK
      // As the 'apply' method in the JobRepository returns a behaviour this is the same as returning a new behaviour
      // the same as this, but with an updated jobs map
      JobRepository(jobs.+(job.id -> job))
    case GetJobById(id, replyTo) =>
      replyTo ! jobs.get(id)
      Behaviors.same
    case ClearJobs(replyTo) =>
      replyTo ! OK
      JobRepository(Map.empty)
  }

}

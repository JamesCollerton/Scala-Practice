package org.example.application
package actors

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import scala.concurrent.duration._
import scala.concurrent.Future

class JobRoutes(buildJobRepository: ActorRef[JobRepository.Command])(implicit system: ActorSystem[_]) extends JsonSupport {

  import akka.actor.typed.scaladsl.AskPattern.schedulerFromActorSystem
  import akka.actor.typed.scaladsl.AskPattern.Askable

  /*
   Asking someone requires a timeout and a scheduler, if the timeout hits without response the ask is failed with a TimeoutException,
   note that this is implicitly done
   */
  implicit val timeout: Timeout = 3.seconds

  lazy val theJobRoutes: Route =
    pathPrefix("jobs") {
      concat(
        pathEnd {
          concat(
            post {
              /*
                entity(as[JobRepository.Job]) seems to be the standard way of specifying what we think the message body
                will be.
               */
              entity(as[JobRepository.Job]) { job =>
                val operationPerformed: Future[JobRepository.Response] =
                  buildJobRepository.ask(JobRepository.AddJob(job, _))
                onSuccess(operationPerformed) {
                  case JobRepository.OK         => complete("Job added")
                  case JobRepository.KO(reason) => complete(StatusCodes.InternalServerError -> reason)
                }
              }
            },
            delete {
              val operationPerformed: Future[JobRepository.Response] =
                buildJobRepository.ask(JobRepository.ClearJobs)
              onSuccess(operationPerformed) {
                case JobRepository.OK         => complete("Jobs cleared")
                case JobRepository.KO(reason) => complete(StatusCodes.InternalServerError -> reason)
              }
            }
          )
        },
        /*
          The ampersand is an equivalent to nesting paths
         */
        (get & path(LongNumber)) { id =>
          val maybeJob: Future[Option[JobRepository.Job]] = {
            buildJobRepository.ask(JobRepository.GetJobById(id, _))
          /*
            rejectEmptyResponse is wrapped round the complete method to make a 404 if nothing is found
           */
          }
          rejectEmptyResponse {
            complete(maybeJob)
          }
        }
      )
    }
}

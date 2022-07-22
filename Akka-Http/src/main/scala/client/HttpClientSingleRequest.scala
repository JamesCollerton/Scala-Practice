package org.example.application
package org.example.application.client

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

/*
  request-level API

  https://doc.akka.io/docs/akka-http/current/client-side/request-level.html

  Most often, your HTTP client needs are very basic. You need the HTTP response for a certain request and don’t want to
  bother with setting up a full-blown streaming infrastructure.

  For these cases Akka HTTP offers the Http().singleRequest(...) method, which turns an HttpRequest instance into
  Future[HttpResponse]. Internally the request is dispatched across the (cached) host connection pool for the request’s
  effective URI.
 */
object HttpClientSingleRequest {

  def main(args: Array[String]): Unit = {

    /*
      Here we create an Actor System in order to implictly use it when executing our single request.
     */
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")

    /*
     Needed for the future flatMap/onComplete in the end
     */
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://akka.io"))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_) => sys.error("something wrong")
      }
  }
}

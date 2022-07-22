package org.example.application
package server

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

/*
  Basic example of HTTP routing

  How do exceptions/ errors work?
    A Route can be “sealed” using Route.seal, which relies on the in-scope RejectionHandler and ExceptionHandler
    instances to convert rejections and exceptions into appropriate HTTP responses for the client. Sealing a Route is
    described more in detail later.
    https://doc.akka.io/docs/akka-http/current/routing-dsl/rejections.html
    https://doc.akka.io/docs/akka-http/current/routing-dsl/exception-handling.html

  What is a No-Op?

  Implications of Streaming Nature of Requests/ Responses

  Essentially, using a streaming framework means lack of consumption of the HTTP Entity, is signaled as back-pressure
  to the other side of the connection. This means clients must consume responses for the connection to be freed up by
  Akka.

  This is a feature as it means responses can be consumed only on demand when the client is ready to consume the bytes.

  https://doc.akka.io/docs/akka-http/current/implications-of-streaming-http-entity.html

  Become pattern
 */
object HttpServerRoutingMinimal {

  def main(args: Array[String]): Unit = {

    /*
      Here we define the ActorSystem for the actors. An ActorSystem is a heavyweight structure that will allocate 1…N
      Threads, so create one per logical application.

      Actor systems are a little different to layered software design. Rather than handling errors defensively, or throwing
      exceptions, we can hand errors off to actors who know how to deal with them
     */
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")

    /*
      Provides an implicit execution context for use with threading. Needed for the future flatMap/onComplete in the end
     */
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    /*
      Here we define a route, which takes a route context, and returns a future of a route result.

      The “Route” is the central concept of Akka HTTP’s Routing DSL. All the structures you build with the DSL, no
      matter whether they consists of a single line or span several hundred lines, are type turning a RequestContext
      into a Future[RouteResult].

      Generally when a route receives a request (or rather a RequestContext for it) it can do one of these things:
        - Complete the request by returning the value of requestContext.complete(...)
        - Reject the request by returning the value of requestContext.reject(...) (see Rejections)
        - Fail the request by returning the value of requestContext.fail(...) or by just throwing an exception (see Exception Handling)
        - Do any kind of asynchronous processing and instantly return a Future[RouteResult] to be eventually completed later

      Essentially, when you combine directives and custom routes via the concat method, you build a routing structure
      that forms a tree.

      You can combine routes from multiple files as shown below
        https://stackoverflow.com/questions/34514372/akka-http-with-multiple-route-configurations
     */
    val route: Route = {
      /*
        Used to match requests against a path

        Automatically adds a leading slash to its PathMatcher argument, you therefore don’t have to start your matching
        expression with an explicit slash.
       */
      path("hello") {
        /*
          Get is then a directive. Directives create Routes. This looks to be a more complex internal thing, and so
          may not be worth worrying about for the time being
         */
        get {
          /*
           This complete returns a route. The HttpEntity class is used to hold the response, assuming it defaults to
           a 200
           */
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }
    }

    /*
      Now we bind the route to the server to make the routes available
     */
    val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}

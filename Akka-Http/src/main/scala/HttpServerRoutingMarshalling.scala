package org.example.application

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.io.StdIn
import scala.concurrent.Future

object HttpServerRoutingMarshalling {

  implicit val system = ActorSystem(Behaviors.empty, "SprayExample")
  implicit val executionContext = system.executionContext

  var orders: List[Item] = Nil

  /*
    These are the domain model for the example. An item is what we return from the find route, which is automatically
    serialized using Spray. I'm assuming this is done through an implicit support.
   */
  final case class Item(name: String, id: Long)
  final case class Order(items: List[Item])

  /*
    Formats for unmarshalling and marshalling, these are implicitly used for marshalling/ unmarshalling. We could
    potentially store them in the object for Item and Order to make them available everywhere
   */
  implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item)
  implicit val orderFormat: RootJsonFormat[Order] = jsonFormat1(Order)

  /*
    Fake async database query API. Notice how it generates a Future from the query
   */
  def fetchItem(itemId: Long): Future[Option[Item]] = Future {
    orders.find(o => o.id == itemId)
  }

  /*
    Fake async database query which is used to 'save' an item into the orders list. It returns a 'Done' from the future
    to show it's immediately finished.
   */
  def saveOrder(order: Order): Future[Done] = {
    orders = order match {
      case Order(items) => items ::: orders
      case _            => orders
    }
    Future { Done }
  }

  def main(args: Array[String]): Unit = {
    val route: Route =
      concat(
        get {
          /*
            Defines a path. The pathPrefix directive is used to match all paths beginning with the phrase (in this
            case item, remember the automatic leading /)

            The part afterwards is a pathMatcher
            https://doc.akka.io/docs/akka-http/current/routing-dsl/path-matchers.html?language=scala#pathmatcher-dsl
           */
          pathPrefix("item" / LongNumber) { id =>
            // there might be no item for a given id
            val maybeItem: Future[Option[Item]] = fetchItem(id)

            /*
              On success is another Akka Http concept. This finalises the future and then passes the result over to
              the final
             */
            onSuccess(maybeItem) {
              case Some(item) => complete(item)
              case None       => complete(StatusCodes.NotFound)
            }
          }
        },
        post {
          path("create-order") {
            entity(as[Order]) { order =>
              val saved: Future[Done] = saveOrder(order)
              onSuccess(saved) { _ => // we are not interested in the result value `Done` but only in the fact that it was successful
                complete("order created")
              }
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

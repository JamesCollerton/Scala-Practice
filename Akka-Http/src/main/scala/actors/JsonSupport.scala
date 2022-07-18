package org.example.application
package actors

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.json.DeserializationException
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat

/*
  This contains all of the custom mapping for statuses. Our statuses are defined:

    sealed trait Status
    object Successful extends Status
    object Failed extends Status

    We don't really want to return objects, and instead want to return just the strings "Failed" and "Successful".
    Similarly we don't want to take in objects, we want strings instead.
 */
trait JsonSupport extends SprayJsonSupport {
  /*
   Import the default encoders for primitive types (Int, String, Lists etc)
   */
  import DefaultJsonProtocol._
  import JobRepository._

  implicit object StatusFormat extends RootJsonFormat[Status] {
    def write(status: Status): JsValue = status match {
      case Failed     => JsString("Failed")
      case Successful => JsString("Successful")
    }

    def read(json: JsValue): Status = json match {
      case JsString("Failed")     => Failed
      case JsString("Successful") => Successful
      case _                      => throw new DeserializationException("Status unexpected")
    }
  }

  /*
    Here we also define the implicit formatter for the overall Job
   */
  implicit val jobFormat: RootJsonFormat[Job] = jsonFormat4(Job)
}

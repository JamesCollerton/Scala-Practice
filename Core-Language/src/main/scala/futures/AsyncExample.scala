package org.example.application
package futures

import scala.concurrent.Future
import scala.async.Async.{async, await}

object AsyncExample {

  /*
    There are pretty much two methods in the Async library:
      - Async, which executes something and returns a future
      - Await, await until a future has completed
   */
  def main(args: Array[String]): Unit = {

  }

  def asyncExample(): Unit = {
    /*
      This SYNCHRONOUS function is used to check if a record exists
     */
    def recordExists(id: Long): Boolean = {
      println(s"Checking if record $id exists")
      Thread.sleep(1)
      id > 0
    }

    /*
      This SYNCHRONOUS function is used to get a record (assuming we've already checked it exists)
    */
    def getRecord(id: Long): (Long, String) = { //
      println(s"Getting record $id")
      Thread.sleep(1)
      (id, s"record: $id")
    }

    /*
      This defines an asynchronous way of dealing with getting a record
     */
    def asyncGetRecord(id: Long): Future[(Long, String)] = async { //
      val exists = async { val b = recordExists(id); println(b); b }
      if (await(exists)) await(async { val r = getRecord(id); println(r); r })
      else (id, "Record not found!")
    }
  }

}

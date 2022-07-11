package org.example.application
package futures

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object AsyncExample {

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
  def getRecord(id: Long): (Long, String) = {
    println(s"Getting record $id")
    Thread.sleep(1)
    (id, s"record: $id")
  }

  /*
    This defines an asynchronous way of dealing with getting a record
   */

  /*
    So async is just a wrapper. Notice how the function itself returns a tuple of (Long, String), but then
    async means it returns a future.
   */
  def asyncGetRecord(id: Long): Future[(Long, String)] = async {

    /*
      Notice how we're wrapping just normal functions in async/ await. We would still need to map around them
      if we were to combine futures etc.
     */
    val exists = async {
      val b = recordExists(id);
      println(b);
      b
    }
    if (await(exists)) await(async {
      val r = getRecord(id);
      println(r);
      r
    })
    else (id, "Record not found!")
  }

  /*
    There are pretty much two methods in the Async library:
      - Async, which executes something and returns a future
      - Await, await until a future has completed
   */
  def main(args: Array[String]): Unit = {
    Await.ready(asyncGetRecord(1), Duration.Inf)
    Await.ready(asyncGetRecord(0), Duration.Inf)
    Await.ready(asyncGetRecord(1), Duration.Inf)
  }

}

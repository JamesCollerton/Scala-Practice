package org.example.application
package functional

import scala.util.control.TailCalls.{TailRec, done, tailcall}

object TailCallsExamples {

  /*
    The use of tail calls is helpful in that it allows us to do something similar to the tail recursive annotation,
    but for functions that ping pong between each other.
   */
  def isEven(xs: List[Int]): TailRec[Boolean] =
    if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))

  def isOdd(xs: List[Int]): TailRec[Boolean] =
    if (xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 5) {
      val even = isEven((1 to i).toList)
      println(s"$i is even? $even")
    }
  }
}

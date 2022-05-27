package org.example.application
package basic

import scala.annotation.tailrec

object NestedFunctionsExample {

  /*
    This is an example of how we can use an inner function to hide complexity from the outside world. We can mutate the
    accumulator but not expose the mutation outside the function.
   */
  def sum(list: List[Int]): Int = {
    var accumulator = 0;

    @tailrec
    def sumStep(list: List[Int]): Unit = {
      if(list.nonEmpty) {
        accumulator += list.head
        sumStep(list.tail)
      }
    }

    sumStep(list)

    accumulator
  }

  def main(args: Array[String]): Unit = {
    println(sum(List(1, 2, 3)))
  }

}

package org.example.application
package functional

import scala.annotation.tailrec

object TailRecursiveExamples {

  def main(args: Array[String]): Unit = {
    println(s"Factorial of 10: ${factorial(10)}")
  }

  /*
    Here we use a tail recursive call. These are designed so that the functional programming orientated recursion is
    resolved to a loop
   */
  def factorial(i: Int): Int = {

    @tailrec
    def step(curr: Int, acc: Int): Int = {
      if(curr < 1) acc
      else step(curr - 1, curr * acc)
    }

    step(i ,1)
  }

}

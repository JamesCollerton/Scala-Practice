package org.example.application
package basic

/*
  Partial functions are used when we want to define a function for a subset of a type. For example, if we want to
  have a divide function that takes all values but zero.
 */
object PartialFunctionExample {

  def main(args: Array[String]): Unit = {
    println(divide.isDefinedAt(0))
    println(divide(1))
    println(findNumber(3))
  }

  /*
    This is the shorthand version of divide, which takes in an int and then matches to see if it is not zero. It seems
    to use a case statement and if nothing is matched then it is not defined
   */
  val divide: PartialFunction[Int, Int] = {
    case d: Int if d != 0 => 42 / d
  }

  /*
    This is the long hand version of the above, and shows how the method is expanded.
   */
  val divideVerbose: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
    def apply(x: Int): Int = 42 / x
    def isDefinedAt(x: Int): Boolean = x != 0
  }

  /*
    The below shows how we can compose partial functions into another partial function
   */
  val isOne: PartialFunction[Int, String] = {
    case d: Int if d == 1 => "It's one!"
  }

  val isTwo: PartialFunction[Int, String] = {
    case d: Int if d == 2 => "It's two!"
  }

  val isAnything: PartialFunction[Int, String] = {
    case _: Int => "It's not one or two!"
  }

  val findNumber: PartialFunction[Int, String] = isOne orElse isTwo orElse isAnything
}

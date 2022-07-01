package org.example.application
package forcomprehensions

import scala.util.{Failure, Success, Try}

object BasicForExamples {

  def main(args: Array[String]): Unit = {

    /*
      for comprehensions contain one or more generator expressions, plus optional guard
      expressions (for filtering), and value definitions. The output can be “yielded” to create
      new collections or a side-effecting block of code can be executed on each pass, such as
      printing output.
     */

    val oddList = for {
      i <- 1 to 10
      evenOdd = if (i % 2 == 0) "even" else "odd"
    } yield evenOdd

    println(s"Even, odd list $oddList")

    /*
      We can also use for comprehensions with options. This allows us to skip unpopulated options
     */

    def isPositive(x: Int): Option[Int] = {
      if (x > 0) Some(x) else None
    }

    val noneSumResult: Option[Int] = for {
      a <- isPositive(2)
      b <- isPositive(0)
      c <- isPositive(2)
    } yield (a + b + c)

    println(s"None sum result $noneSumResult")

    val sumResult: Option[Int] = for {
      a <- isPositive(2)
      b <- isPositive(2)
      c <- isPositive(2)
    } yield (a + b + c)

    println(s"Sum result $sumResult")

    eitherExample()
    tryExamples()
  }

  def eitherExample(): Unit = {

    /*
      Either has two subclasses: left and right, which are used to represent the two values. I think generally right is
      used for success, left is used for failure. This means that now Either is right-biased.
     */

    def positive(i: Int): Either[String, Int] = {
      if (i > 0) Right(i) else Left(s"nonpositive number $i")
    }

    /*
      So here we go along each of the different numbers. If we get a right result then we keep going, if we get a left
      result then we yield this at the bottom.

      If all numbers are in the right hand side then we unbox them at the final step and return the result
     */
    val rightPositive = for {
      i1 <- positive(5)
      i2 <- positive(10 * i1)
      i3 <- positive(25 * i2)
      i4 <- positive(2 * i3)
    } yield (i1 + i2 + i3 + i4)

    println(s"Right positive result: $rightPositive")

    val leftNegative = for {
      i1 <- positive(5)
      i2 <- positive(-1 * i1)
      i3 <- positive(25 * i2)
      i4 <- positive(-2 * i3)
    } yield (i1 + i2 + i3 + i4)

    println(s"Left negative result: $leftNegative")

    val eitherLeft: Either[String, Int] = Left("This is an error message")
    val eitherRight: Either[String, Int] = Right(2)

    /*
      This is how mapping works on eithers. If we call the map function on a component that doesn't exist (for example
      the right of a left or the left of a right) we simply pass through the left or right.
     */
    println(s"Left map length on left ${eitherLeft.left.map(_.length)}")
    println(s"Left map length on right ${eitherRight.left.map(_.length)}")
    println(s"Right map to double on left ${eitherLeft.map(_.toDouble)}")
    println(s"Right map to double on right ${eitherRight.map(_.toDouble)}")

    println(s"Doing valid addition of ints ${addIntsEither("1", "2")}")
    println(s"Doing invalid addition of ints ${addIntsEither("invalid", "2")}")
  }

  def addIntsEither(x: String, y: String): Either[Exception, Int] = {
    try {
      Right(x.toInt + y.toInt)
    } catch {
      case nfe: NumberFormatException => Left(nfe)
    }
  }

  def tryExamples(): Unit = {
    /*
      Try is defined Try[<Right Type>] and is incredibly similar to an either, except for the fact that the left is
      always a throwable.
     */

    println(s"Add ints try, two valid ${addIntsTry("1", "2")}")
    println(s"Add ints try, two invalid ${addIntsTry("invalid", "2")}")

    /*
      How we can use tries to get the result and use it effectively
     */
    val addIntsTryResult = addIntsTry("1", "2") match {
      case Success(x) => x
      case Failure(exception) => throw exception
    }

    println(s"Printing addIntsTryResult $addIntsTryResult")
  }

  def addIntsTry(x: String, y: String): Try[Int] = {
    try {
      Success(x.toInt + y.toInt)
    } catch {
      case e: NumberFormatException => Failure(e)
    }
  }
}

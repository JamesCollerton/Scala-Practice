package org.example.application
package forcomprehensions

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
      evenOdd =  if (i % 2 == 0) "even" else "odd"
    } yield evenOdd

    println(s"Even, odd list $oddList")

      /*
        We can also use for comprehensions with options. This allows us to skip unpopulated options
       */

    def isPositive(x : Int): Option[Int] = {
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
  }

  def eitherExample(): Unit = {
    
  }

}

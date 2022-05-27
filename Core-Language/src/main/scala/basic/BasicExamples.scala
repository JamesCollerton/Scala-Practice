package org.example.application
package basic

/*
  Object is essentially a singleton, hence we can call the methods directly on it. All the member variables it holds
  are static.

  Difference between object, class, case class and traits
 */
object BasicExamples{
  def main(args: Array[String]): Unit = {

    println("Hello from main of object")

    /*
      There are two types of variable: val and var. Val is used for immutable values (i.e. we can't point them to
      something else), var is used for mutable ones
     */
    val companionClassTuple = CompanionClass.unapply(CompanionClass(1, 2))

    println(companionClassTuple)

    /*
      We can do the below to create ranges of numbers. We can also use the key word 'until'
     */
    println(1 to 10 by 3)
    println('a' to 'i' by 3)

  }
}

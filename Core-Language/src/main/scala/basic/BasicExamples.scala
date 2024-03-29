package org.example.application
package basic

/*
  Object is essentially a singleton, hence we can call the methods directly on it. All the member variables it holds
  are static.

  Difference between object, class, case class and traits

  - Spread method
  - Yield
  - forSome
  - Pattern Matching
  - Sealed, sealed abstract classes
  - Traits
  - Type comprehensions
  - Type projections
  - Generics, parameterized types, covariant/ contravariant typing,
  - Option, some, none
  - Ammonite
  - Sealed class hierarchies
  - Package objects
  - What is lifting a function?
  - Zip functions
  - Directives
  - Futures
 */
object BasicExamples{
  def main(args: Array[String]): Unit = {

    println("Hello from main of object")

    val immutableString = "Can't change me!"
    var mutableString = "Change me!"

    println(immutableString)
    println(mutableString)

    mutableString = "I've been changed!"

    println(mutableString)

    val explicitString: String = "Explicit String!"

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

    /*
      Function literals
     */
    val doubleInt: (Int) => Int = (a: Int) => 2 * a;
    println(s"Doubled: ${doubleInt(2)}")

    // If statement results can be assigned to something
    val ifResult = if(1 % 2 == 0) "This is impossible!" else "This is the result"
    println(ifResult)
  }
}

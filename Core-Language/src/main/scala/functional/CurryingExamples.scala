package org.example.application
package functional

object CurryingExamples {

  def main(args: Array[String]): Unit = {

    val partiallyApplied1 = curriedCat1("Hello!")(_)
    val partiallyApplied2 = curriedCat2("Hello!")

    println(s"PA 1: ${partiallyApplied1("World!")}")
    println(s"PA 2: ${partiallyApplied2("World!")}")

    // Note, uncurriedCat _ converts a method with multiple arguments into a curried function
    val curriedFunction = (uncurriedCat _).curried
    val uncurriedFunction = Function.uncurried(curriedFunction)

    println(s"Uncurried function: ${uncurriedFunction("Uncurried", "successfully")}")
  }

  /*
    Curried functions always have multiple argument lists, each with a single argument
   */
  def curriedCat1(s1: String)(s2: String): String = {
    s"$s1 $s2"
  }

  /*
    We can also declare curried functions as below
   */
  def curriedCat2(s1: String): String => String = (s2: String) => s"$s1 $s2"

  def uncurriedCat(s1: String, s2: String): String = s"$s1 $s2"
}

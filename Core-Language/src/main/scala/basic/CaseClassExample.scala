package org.example.application
package basic

/*
  Case classes are generally used for modelling immutable data. We can instantiate them without the new keyword as they
  implicitly have an object with an apply method.

  Notice how we've specified defaults for the values, which will be picked up if nothing else is specified.
 */
case class CaseClass(x: Int = 0, y: Int = 0)

object CaseClassExample {

  def main(args: Array[String]): Unit = {
    val c1 = CaseClass(y = 1)

    // Here we can create a shallow clone of the object. We can use this to prove that comparison is done by value, not
    // reference
    val c1Copy = c1.copy()

    println(c1 == c1Copy)
  }

}

package org.example.application
package basic

object ObjectExample {

  val field = "Field"

  // Here we declare a function in Scala. We use the def keyword to define a method, then
  // give the argument name and type, followed by the return type. Notice how we don't need
  // the return keyword, we can default to the last line in the function.
  def methodWithArgument(argument: String): String = {
    s"This argument $argument is returned from the method";
  }
}

object ScalaMain {
  def main(args: Array[String]): Unit = {

    // Now we assign that function to a variable. We no longer need the functional interface, and Scala
    // treats the function as a variable rather than an object. The syntax below denotes a function that
    // takes a String and returns a String
    val methodWithArgumentReference: String => String = x => ObjectExample.methodWithArgument(x)

    // Here we call the function, note we no longer need the apply method.
    methodWithArgumentReference("'calling the method with this argument'")
  }
}
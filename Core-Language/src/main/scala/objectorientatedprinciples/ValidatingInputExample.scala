package org.example.application
package objectorientatedprinciples

object ValidatingInputExample {
  def main(args: Array[String]): Unit = {

    ExampleCaseClass(1)
    ExampleCaseClass(0)

  }

  /*
    Here we see we can add a requirement for certain properties of the class to make it valid
   */
  case class ExampleCaseClass(x: Int) {
    require(valid(x), s"Invalid input specified: $x")

    protected def valid(x: Int): Boolean = x > 0
  }
}

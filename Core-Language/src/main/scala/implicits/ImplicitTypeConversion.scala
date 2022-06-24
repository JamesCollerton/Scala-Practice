package org.example.application
package implicits

object ImplicitTypeConversion {

  /*
    Here we declare an implicit class. When below we see there is no 'implicitFunction' declared on a string and so
    it implictly falls back on this.
   */
  implicit final class ListAssoc[A](val self: A) {
    def implicitFunction(y: A): List[A] = List(self, y)
  }

  def main(args: Array[String]): Unit = {
    println("Hello" implicitFunction "World")
  }

}

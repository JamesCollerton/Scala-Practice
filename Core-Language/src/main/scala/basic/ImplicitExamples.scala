package org.example.application
package basic

abstract class Monoid[A] {
  def add(x: A, y: A): A
  def unit(): A
}

object ImplicitExamples {

  /*
    Here we create an anonymous implementation of the Monoid class, overriding the two methods defined in the abstract
    class. We also mark it implicit, showing we can use it in implicit functions
   */
  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def add(x: String, y: String): String = x concat y
    def unit(): String = ""
  }

  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    def add(x: Int, y: Int): Int = x + y
    def unit(): Int = 0
  }

  /*
    Here we have a method which takes in two arguments, a list and an implicit monoid. This monoid can resolve
    itself using the above functions depending on the inferred type from the first argument.
   */
  def sum[A](list: List[A])(implicit m: Monoid[A]): A = {
    if(list.isEmpty) {
      m.unit()
    } else {
      m.add(list.head, sum(list.tail))
    }
  }

  def main(args: Array[String]): Unit = {
    println(sum(List(1, 2, 3)))
    println(sum(List("a", "b", "c")))
  }

}

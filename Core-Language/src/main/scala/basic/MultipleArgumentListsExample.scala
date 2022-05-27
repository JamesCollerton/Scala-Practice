package org.example.application
package basic

object MultipleArgumentListsExample {

  def main(args: Array[String]): Unit = {
    println(mapFunction(List(1, 2, 3))(x => x * x))

    /*
      Here we can see a partially applied function, where we pass the second argument a set function, creating another
      function assigned to doubleList. This means each time we pass a list all of the elements are doubled
     */
    val doubleList = mapFunction(_: List[Int])(2 * _)

    println(doubleList(List(1, 2, 3)))
  }

  /*
    Here we define a function which takes two separate LISTS of arguments. One for the thing we would like to carry out
    our operation on, and one for the operation itself.
   */
  def mapFunction[A, B](l: List[A])(f: A => B): List[B] = {
    l.map(f)
  }

}

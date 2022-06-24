package org.example.application
package implicits

import math.Ordering

object ImplictlyExample {

  def main(args: Array[String]): Unit = {
    val list = MyList(List(1, 2, 3))
    println(list.sortBy1(i => -i))
    println(list.sortBy2(i => -i))
  }

  case class MyList[A](list: List[A]) {

    /*
      This second argument takes something that is similar to the Java comparable function, i.e. it compares two things
      of type B and returns their ordering.

      The initial function is used to convert the list from type A to type B, the second argument is what supplies the
      comparator. So in the first example what's happening is we're converting the list to negative numbers, then sorting
      in increasing order (hence the reversal).
     */
    def sortBy1[B](f: A => B)(implicit ordering: Ordering[B]): List[A] = {
      list.sortBy(f)(ordering)
    }

    /*
      Here we use a context bound (B : Ordering), which implies the second parameter as above. We no longer have a
      variable name for it, so need to use implictly to reference it
     */
    def sortBy2[B: Ordering](f: A => B): List[A] = {
      list.sortBy(f)(implicitly[Ordering[B]])
    }
  }
}

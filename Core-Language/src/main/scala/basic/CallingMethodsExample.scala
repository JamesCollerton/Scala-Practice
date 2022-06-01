package org.example.application
package basic

object CallingMethodsExample {

  def main(args: Array[String]): Unit = {

    val isEven: Int => Boolean = _ % 2 == 0

    List(1, 2, 3, 4).filter(i => isEven(i)).foreach(i => println(i))
    List(1, 2, 3, 4).filter(isEven(_)).foreach(println(_))
    List(1, 2, 3, 4).filter(isEven).foreach(println)
    List(1, 2, 3, 4) filter isEven foreach println
  }

}

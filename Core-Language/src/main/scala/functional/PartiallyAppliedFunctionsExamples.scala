package org.example.application
package functional

object PartiallyAppliedFunctionsExamples {

  def main(args: Array[String]): Unit = {

    def twoArgumentFunction(a: Int)(b: Int): Int = {
      a * b
    }

    val double = twoArgumentFunction(_: Int)(2)

    println(s"Double 2 is ${double(2)}")
  }

}

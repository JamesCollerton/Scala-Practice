package org.example.application
package implicits

object SimpleTraitExample {

  def main(args: Array[String]): Unit = {

    implicit val implicitFunction: Function[Int, Int] = x => 2 * x

    def functionUsingImplicit(x: Int)(implicit implicitFunction: Function[Int, Int]): Int = implicitFunction(x)

    println(s"Function result: ${functionUsingImplicit(2)}")
  }

}

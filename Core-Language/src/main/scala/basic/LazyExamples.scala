package org.example.application
package basic

object LazyExamples {

  def create(): String = {
    println("Creating!")
    "Created"
  }

  def main(args: Array[String]): Unit = {

    lazy val lazyVal = create()

    println(lazyVal)

  }

}

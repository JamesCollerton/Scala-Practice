package org.example.application
package sandpit

object Test {

  def main(args: Array[String]): Unit = {
    val example = Example(Map("1" -> "Existing!"))

  }

}

object Example {

  def apply(jobs: Map[String, String] = Map.empty): Unit = {}

}

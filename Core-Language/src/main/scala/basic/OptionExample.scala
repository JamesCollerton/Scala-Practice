package org.example.application
package basic

object OptionExample {

  def main(args: Array[String]): Unit = {

    val populationMap = Map(
      "UK" -> 60000000,
      "France" -> 70000000
    )

    // This prints the UK population but if there is no key for the map then it will throw an error. It is better
    // to use the methods below as an option safe version.
    println(populationMap("UK"))

    // This prints some, some, none, 0.
    println(populationMap.get("UK"))
    println(populationMap.get("France"))
    println(populationMap.get("Germany"))
    println(populationMap.getOrElse("Germany", 0))

    // This is a use of either, which has two components: left and right. The left is by convention the
    // failure mode (something went wrong), and the right is the success mode. We might use this for passing
    // an error message through functions or similar.
    val in = "This is a string"
    val result: Either[String,Int] =
      try Right(in.toInt)
      catch {
        case _: NumberFormatException => Left(in)
      }

    val matched = result match {
      case Right(x) => s"This was successfully recognised as the int: $x"
      case Left(x)  => s"This was recognised as string: $x"
    }

    println(matched)

  }

}

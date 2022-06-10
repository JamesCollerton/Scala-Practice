package org.example.application
package patternmatching

object PatternMatchingExamples {

  def main(args: Array[String]): Unit = {

    // Simple matching. Here we are just checking against the boolean value to see if it's true or false
    val bool = true
    bool match {
      case true => println("Boolean is true")
      case _ => println("Boolean is false")
    }

    // Showing how we can match on multiple different types
    val mixedTypeSequence = Seq(None, 1, 1.0, "String", 'a')

    for(i <- mixedTypeSequence) {
      i match {
        case None => println("Detected None")
        case _: Int | _: Double  => println(s"Detected number $i")
        case s: String => println(s"Detected String $s")
        case c: Char => println(s"Detected Character $c")
      }
    }

  }

}

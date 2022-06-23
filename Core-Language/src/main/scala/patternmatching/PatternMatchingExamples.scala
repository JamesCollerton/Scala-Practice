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

    // Now we look into how we can decompose sequences and use pattern matching

    val normalSequence = Seq(1, 2, 3)
    val mapSequence = Map(1 -> "A", 2 -> "B", 3 -> "C").toSeq
    val emptySequence = Nil

    val sequenceOfSequences = Seq(normalSequence, mapSequence, emptySequence)

    for(sequence <- sequenceOfSequences) {
      println(sequenceMatcher(sequence))
    }

    val tuple = ("A", "B", "C")

    tuple match {
      case (_, "A", _) => println("No!")
      case (_, "B", "A") => println("No!")
      case (_, "B", "C") => println("Yes!")
    }

    tuple match {
      case (_, "A", _) => println("No!")
      case (_, "B", "A") => println("No!")
      case (_, "B", x) if "C" equals x => println("Yes again!")
    }

    val person = Person("Jack", 10)

    person match {
      case Person(_, 11) => println("No, shouldn't match 11!")
      case Person("Joe", _) => println("No, shouldn't match Joe!")
      case Person(_, 10) => println("Yes, matches 10!")
    }

    person match {
      case Person(name, age) => println(s"This person is called $name and is $age years old")
    }

    val rocco = Dog("Rocco", 1)

    rocco match {
      case Dog(_, _) => println("Should print unapplying above...")
    }

    val gus = Dog("Gus", 2)

    /*
      This is another example of using the unapply method to pattern match. Notice how we pass
      two strings to the class, rather than a string and an int. This is used in the matching from
      the unapply method
     */
    val matchedGusString = gus match {
      case Dog("Gus", "A") => "This shouldn't match for Gus!"
      case Dog("Dog name: Gus", "Dog age: 2") => "This should match for Gus!"
      case _ => "This shouldn't match for Gus!"
    }

    println(matchedGusString)
  }

  def sequenceMatcher[A](seq: Seq[A]): String = {
    seq match {
      case head +: tail => s"$head " + sequenceMatcher(tail)
      case Nil => ""
    }
  }

  case class Person(name: String, age: Int)

}

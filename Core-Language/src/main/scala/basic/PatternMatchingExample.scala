package org.example.application
package basic


object PatternMatchingExample {
  def main(args: Array[String]): Unit = {

    println(matchCaseClass(CaseClass(1, 1)))
    println(matchCaseClass(CaseClass(1)))
    println(matchCaseClass(CaseClass(y = 1)))
    println(matchCaseClass(CaseClass(2, 2)))
    println(matchCaseClass("This is a string!"))

  }

  def matchCaseClass(a: Any): String = a match {
    case CaseClass(1, 1) => "Tuple (1, 1)"
    case CaseClass(_, 1) => "Tuple (_, 1)"
    case CaseClass(1, _) => "Tuple (1, _)"
    case CaseClass(_, _) => "Tuple (_, _)"
    case _ => "Something else!"
  }
}

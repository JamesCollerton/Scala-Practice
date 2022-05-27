package org.example.application
package basic


object PatternMatchingExample {
  def main(args: Array[String]): Unit = {

    println(matchCaseClass(CaseClass(1, 1)))
    println(matchCaseClass(CaseClass(1)))
    println(matchCaseClass(CaseClass(y = 1)))
    println(matchCaseClass(CaseClass(2, 2)))
    println(matchCaseClass("A"))
    println(matchCaseClass("This is a string!"))

  }

  /*
    This is used to show how we can intelligently match a case class object. Notice how we define the function to
    directly be a match statement. Then see how we use the _ character to represent 'any'.

    We can add additional logic into the case class matching as in the second to last line. Additionally the last
    line is equivalent to a default in Java
   */
  def matchCaseClass(a: Any): String = a match {
    case CaseClass(1, 1) => "Tuple (1, 1)"
    case CaseClass(_, 1) => "Tuple (_, 1)"
    case CaseClass(1, _) => "Tuple (1, _)"
    case CaseClass(_, _) => "Tuple (_, _)"
    case s: String if s == "A"  => "String is A"
    case _ => "Something else!"
  }
}

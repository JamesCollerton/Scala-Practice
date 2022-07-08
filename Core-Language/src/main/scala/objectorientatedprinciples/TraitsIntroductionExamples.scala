package org.example.application
package objectorientatedprinciples

/*
  Mixing together different classes while avoiding the need to extending multiple concrete classes
 */
object TraitsIntroductionExamples {
  def main(args: Array[String]): Unit = {

  }

  trait PersonState {
    val name: String
    val age: Int
  }

  trait EmployeeState {
    val employeeId: String
  }

  case class Employee(name: String, age: Int, employeeId: String) extends PersonState with EmployeeState
}

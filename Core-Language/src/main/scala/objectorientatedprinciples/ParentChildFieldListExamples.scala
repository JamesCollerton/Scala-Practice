package org.example.application
package objectorientatedprinciples

object ParentChildFieldListExamples {

  def main(args: Array[String]): Unit = {
    new SealedChildClass(1, 2).print()
  }

  sealed abstract class SealedAbstractParentClass(val valueOne: Int) {
    def print(): Unit = {
      println(s"Value one $valueOne")
    }
  }

  sealed class SealedChildClass(valueOne: Int, val valueTwo: Int) extends SealedAbstractParentClass(valueOne) {
    override def print(): Unit = {
      println(s"Value one $valueOne, value two $valueTwo")
    }
  }

}

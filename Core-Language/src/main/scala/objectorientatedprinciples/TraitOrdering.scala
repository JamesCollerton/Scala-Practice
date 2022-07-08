package org.example.application
package objectorientatedprinciples

object TraitOrdering {

  def main(args: Array[String]): Unit = {
    val concreteClass = new ConcreteClass
    concreteClass.methodDefinition()
  }

  /*
    The traits are initialised from left to right, but in the case of inheriting methods it's the rightmost
    trait that gets precedence
   */
  class ConcreteClass extends LeftTrait with RightTrait {
    override def methodDefinition(): Unit = super.methodDefinition()
  }

  trait LeftTrait {
    println("Initialising left trait body!")

    def methodDefinition(): Unit = {
      println("In left trait method definition!")
    }
  }

  trait RightTrait {
    println("Initialising right trait body!")

    def methodDefinition(): Unit = {
      println("In right trait method definition!")
    }
  }
}

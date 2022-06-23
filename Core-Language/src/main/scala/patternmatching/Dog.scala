package org.example.application
package patternmatching

/*
  In this file we create a class and a companion object.
 */
class Dog(val name: String, val age: Int)

/*
  The unapply method is used in the pattern matching case statement
 */
object Dog {
  def apply(name: String, age: Int): Dog = new Dog(name, age)
  def unapply(dog: Dog): Option[Tuple2[String, String]] = {
    println("Unapplying!")
    Some((s"Dog name: ${dog.name}", s"Dog age: ${dog.age}"))
  }
}
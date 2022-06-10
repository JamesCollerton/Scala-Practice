package org.example.application
package basic

object ParameterizedTypesExamples {

  def main(args: Array[String]): Unit = {

    // Covariant typing

    // Here we use the fact that list is defined 'abstract class List[+A]', which essentially means that is B is
    // a subtype of A then List[B] is a subtype of List[A]. As Dog and Cat are both subtypes of Animal then this
    // means that List[Dog] and List[Cat] are both subtypes of List[Animal]
    val dogList = List(Dog("Dog 1"), Dog("Dog 2"), Dog("Dog 3"))
    val catList = List(Cat("Cat 1"), Cat("Cat 2"), Cat("Cat 3"))

    AnimalListPrinter(dogList).print()
    AnimalListPrinter(catList).print()

    // Contravariant typing

    // This is the opposite of covariance typing. If we have Class[-A], making A contravariant implies that for two
    // types A and B where A is a subtype of B, Class[B] is a subtype of Class[A].

    val animalPrinter = new AnimalPrinter
    val catPrinter = new CatPrinter

    printMyCat(animalPrinter, Cat("Animal printer"))
    printMyCat(catPrinter, Cat("Cat printer"))

    // Parameterized class
    ParameterizedClass[String]("Hello").print()
  }

  // Covariant class examples
  abstract class Animal {
    val name: String
  }
  case class Dog(name: String) extends Animal
  case class Cat(name: String) extends Animal

  case class AnimalListPrinter(list: List[Animal]) {
    def print(): Unit = list.foreach(x => println(x.name))
  }

  // Contravariant class examples

  // If we have a class A, which is a subtype of B, then Printer[B] is a subtype of Printer[A]
  abstract class Printer[-A] {
    def print(value: A): Unit
  }

  // At this point it's important to know that Cat is a subtype of Animal. This means Printer[Animal] is a
  // subtype of Printer[Cat]
  class AnimalPrinter() extends Printer[Animal] {
    def print(value: Animal): Unit = println(value.name)
  }
  class CatPrinter extends Printer[Cat] {
    def print(value: Cat): Unit = println(value.name)
  }

  def printMyCat(printer: Printer[Cat], cat: Cat): Unit = {
    printer.print(cat)
  }

  // Parameterised class examples
  case class ParameterizedClass[T](input: T) {
    def print(): Unit = println(input)
  }

}

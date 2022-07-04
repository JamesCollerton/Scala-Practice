package org.example.application
package objectorientatedprinciples

object ImplicitGettersAndSettersExample {
  def main(args: Array[String]): Unit = {
    val name = new Name("James!")
    println(name.value)
    name.value = "Not James!"
  }

  /*
    Here we see that when we pass a field to a class it implicitly creates setters and getters. We can override these
    using the patterns below.
   */
  class Name(s: String) {
    private var _value: String = s

    def value: String = {
      println("In the getter!")
      _value
    }

    def value_=(newValue: String): Unit = {
      println("In the setter!")
      _value = newValue
    }

  }
}

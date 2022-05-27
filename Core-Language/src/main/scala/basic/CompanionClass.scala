package org.example.application
package basic

/*

  This is an example of a companion object/ class.

  It is declared in the same file as a class with the same name. You can access the private fields in the object and
  class from each other.

 */
class CompanionClass(val x: Int, val y: Int) {
  override def toString: String = s"$x, $y"
}

object CompanionClass {
  def createCompanionClass(x: Int, y: Int): CompanionClass = new CompanionClass(x, y)

  /*
    This is the apply method which allows us to create new instances of the Companion class without the 'new' keyword. It
    works as a factory method. You can have multiple apply methods with multiple arguments
   */
  def apply(x: Int, y: Int): CompanionClass = createCompanionClass(x, y)

  /*
    Similarly we can have an unapply method which takes in an instance of the companion object and then deconstructs
    it. Note, we can have multiple of these with different arguments as well.
   */
  def unapply(companionClass: CompanionClass): (Int, Int) = (companionClass.x, companionClass.y)
}

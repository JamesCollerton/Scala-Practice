package org.example.application
package objectorientatedprinciples

/*
  This is an abstract sealed class, so can only be extended by other classes in the file
 */
abstract sealed class AbstractParentClass {
  type In
  val source: In
  def read: String // Read source and return a String
}

/*
  Scala distinguishes between a primary constructor and zero or more auxiliary construc‐
  tors, also called secondary constructors. In Scala, the primary constructor is the entire
  body of the class. Any parameters that the constructor requires are listed after the class
  name. StringBulkReader and FileBulkReader are examples.
 */
class ChildClass(val source: String) extends AbstractParentClass {
  type In = String
  def read: String = source
}
package org.example.application
package basic

/*
  Case classes are generally used for modelling immutable data. We can instantiate them without the new keyword as they
  implicitly have an object with an apply method
 */
case class CaseClassExample(x: Int = 0, y: Int = 0)

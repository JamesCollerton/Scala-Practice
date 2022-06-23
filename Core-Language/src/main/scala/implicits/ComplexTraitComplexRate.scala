package org.example.application
package implicits

case class ComplexTraitCaseClass(m: Int)

object ComplexTraitComplexRate {
  implicit def rate(implicit c: ComplexTraitCaseClass): Int = 2 * c.m
}

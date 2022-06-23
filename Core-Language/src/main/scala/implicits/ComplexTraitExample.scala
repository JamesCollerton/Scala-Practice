package org.example.application
package implicits

object ComplexTraitExample {

  def main(args: Array[String]): Unit = {

    def implicitFunction(x: Int)(implicit rate: Int): Int = rate * x

    {
      import ComplexTraitSimpleRate.rate

      println(s"This should be 4: ${implicitFunction(2)}")
    }

    {
      implicit val complexTraitCaseClass: ComplexTraitCaseClass = ComplexTraitCaseClass(3)
      import ComplexTraitComplexRate.rate

      println(s"This should be 12: ${implicitFunction(2)}")
    }

  }

}

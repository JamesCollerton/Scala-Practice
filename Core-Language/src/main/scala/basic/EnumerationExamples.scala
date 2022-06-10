package org.example.application
package basic

object EnumerationExamples {

  object Breed extends Enumeration {
    type Breed = Value
    val doberman = Value("Doberman Pinscher")
    val yorkie = Value("Yorkshire Terrier")
    val scottie = Value("Scottish Terrier")
    val dane = Value("Great Dane")
    val portie = Value("Portuguese Water Dog")
  }

  def main(args: Array[String]): Unit = {
    println("ID\tBreed")
    for (breed <- Breed.values) println(s"${breed.id}\t$breed")
  }

}

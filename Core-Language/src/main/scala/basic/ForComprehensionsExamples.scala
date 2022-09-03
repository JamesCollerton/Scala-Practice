package org.example.application
package basic

import scala.collection.View.Empty

object ForComprehensionsExamples {

  def main(args: Array[String]): Unit = {

    val dogBreeds = List("Doberman", "Yorkshire Terrier", "Dachshund", "Scottish Terrier", "Great Dane", "Portuguese Water Dog")

    // For comprehension, going round the list
    for(breed <- dogBreeds) println(breed)

    for(breed <- dogBreeds if breed.contains("D")) println("D breed: " + breed)

    val terrierList = for(breed <- dogBreeds if breed.contains("Terrier")) yield breed
    terrierList.foreach(println)

    val catBreeds = List(None, Some("Tabby"), Some("Calico"), Some("Siberian"), Some("Scottish Fold"))

    // Demonstrating looping round and extracting values from optionals
    for {
      Some(breed) <- catBreeds
      upcasedBreed = breed.toUpperCase()
    } println(upcasedBreed)

  }
}
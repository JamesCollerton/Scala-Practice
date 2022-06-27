package org.example.application
package functional

import scala.collection.immutable

object FunctionalOperationsExamples {

  def main(args: Array[String]): Unit = {

    // Traversing
    val dogsMap = Map(
      "Rocco" -> 1,
      "Gus" -> 5,
      "Clyde" -> 10
    )

    dogsMap.foreach(println)

    // Mapping
    val dogsList: List[String] = dogsMap.map(x => s"Dog name $x._1").toList

    println(s"Dogs list: $dogsList")

    val dogsListOfLists: List[List[String]] = dogsMap.map(x => List(x._1)).toList

    // FlatMap behaves exactly like calling map, followed by another method, flatten:
    val dogsFlattenedLists: List[String] = dogsMap.flatMap(x => List(s"Dog name $x._1")).toList

    println(s"Dogs list of lists: $dogsListOfLists")
    println(s"Dogs flattened list: $dogsFlattenedLists")

    // Filtering
    val filteredDogsMap: Map[String, Int] = dogsMap.filter(d => d._2 > 4)

    println(s"Filtered dogs map: $filteredDogsMap")

    extendedFiltering()

    // Folding
    // Reducing

  }

  def extendedFiltering(): Unit = {

    println(s"Dropping first 3 items ${(1 to 10) drop 3}")

    println(s"Dropping first 4 items ${(1 to 10) dropWhile (_ < 5)}")

    println(s"Does 3 exist in range 1 to 10? ${(1 to 10) exists (_ == 3)}")

    println(s"Find 3 in range 1 to 10? ${(1 to 10) filter (_ == 3)}")

    println(s"Find all elements that are not 3 in range 1 to 5? ${(1 to 5) filterNot (_ == 3)}")

    println(s"Finding element 10 in range 1 to 5? ${(1 to 5) find (_ == 10)}")

    println(s"Checking 0, 2, 4, 6, 8, 10 all even? ${(0 to 10 by 2) forall (_ %2 == 0)}")

    println(s"Separating out 0 to 10 by evens and odds ${(0 to 10) partition(_ % 2 == 0)}")

    println(s"Getting first 5 elements of 0 to 10 ${(0 to 10) take 5}")

    println(s"Getting first 7 elements of 0 to 10 ${(0 to 10) takeWhile (_ < 7)}")

  }

}

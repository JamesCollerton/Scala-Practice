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

    foldAndReduce()

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

  def foldAndReduce(): Unit = {

    // Using reduce the initial value is the first value in the list.
    (1 to 10) reduce((acc, x) => {
      println(s"Reduce: accumulator value $acc, reduce value $x")
      acc + x
    })

    // NOTE, we can't reduce an empty collection, however we can use optionReduce
    println(s"Empty list reduce ${List.empty[Int].reduceOption(_ + _)}")

    // Using fold there is no guarantee of the first value in the list. We can use fold left and fold right for this
    ((1 to 10) fold 10) ((acc, x) => {
      println(s"Fold: accumulator value $acc, reduce value $x")
      acc + x
    })

    // We can fold both right and left, left is the default
    ((1 to 5) fold 10) ((acc, x) => {
      println(s"Fold right: accumulator value $acc, reduce value $x")
      acc * x
    })

    // Note, we can rewrite fold left and fold right as /: and :\ respectively

    // Scan is used to generate a sequence of results that are accumulated as we go through
    println(s"Scan result ${((1 to 5) scan 10) (_ + _)}")

    // Fold left and reduce left are tail call recursive and work on infinite collections

    // ((((1 - 2) - 3) - 4) - 5) // = -13 (foldLeft)
    // (-1 + (-2 + (-3 + (-4 + 5)))) = -5 (foldRight)
  }

}

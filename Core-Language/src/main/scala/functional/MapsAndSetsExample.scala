package org.example.application
package functional

object MapsAndSetsExample {

  def main(args: Array[String]): Unit = {
    mapsInitialisation()
    setsInitialisation()
  }

  /*
    This is all the different ways we can initialise a map
   */
  def mapsInitialisation(): Unit = {

    val dogsMap = Map(
      "Rocco" -> 1,
      "Gus" -> 5,
      "Clyde" -> 10
    )

    println(s"Dogs map $dogsMap")

    val dogNameLengthMap = dogsMap map {
      kv => (kv._1, kv._1.length)
    }

    println(s"Dogs name length $dogNameLengthMap")

    val combinedDogsMap = dogsMap + ("Fluffy" -> 7)

    println(s"Combined dogs map $combinedDogsMap")
  }

  /*
  This is all the different ways we can initialise a set
 */
  def setsInitialisation(): Unit = {

    val dogs = Set("Rocco", "Gus", "Clyde")

    println(s"Dogs $dogs")

    val dogsLength = dogs map {d => d.length}

    println(s"Dogs length $dogsLength")

    val combinedDogs = dogs + "Fluffy"

    println(s"Combined dogs $combinedDogs")
  }

}

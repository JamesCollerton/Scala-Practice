package org.example.application
package functional

object DataStructuresExamples {

  /*
    The collection.Seq trait is the abstraction for all mutable and immutable sequential types.

    You tend to have two copies of types in Scala, mutable and immutable
   */
  def main(args: Array[String]): Unit = {

    /*
      How to prepend and append items to lists
     */
    val originalList = List("original item")
    val prefixedList = "first item" +: originalList
    val suffixedList = originalList :+ "secondItem"

    println(s"Prefixed list: ${prefixedList}")
    println(s"Suffixed list: ${suffixedList}")

    /*
      How to create a list with an original list
     */

    val newListWithOriginal = "first item" :: "second item" :: originalList

    println(s"Appended list: $newListWithOriginal")

    /*
      How to create a list with new items using Nil as the terminator
     */

    val newList = "first item" :: "second item" :: Nil

    println(s"Appended list: $newList")

    /*
      How to concatenate lists
     */
    val concatanatedList = List(1, 2) ++ List(3, 4)

    println(s"Concatenated list: $concatanatedList")

    /*
      Note, we're not advised to use List, and instead use Seq. This will allow us to change the implementation.
      However, the following changes are needed:

      :: is replaced by +:
      Nil is replaced by Seq.empty
     */
  }

}

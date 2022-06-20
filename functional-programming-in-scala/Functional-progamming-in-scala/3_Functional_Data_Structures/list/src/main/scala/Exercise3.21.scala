/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.21 - Use flatMap to implement filter.
*
*/

package fpinscala.datastructures


import scala.annotation.tailrec

object FlatMapFilter {

  def filter[A](l: List[A])(pred: A => Boolean):List[A] = 
    FlatMap.flatMap(l){ (x) => if (pred(x)) Cons(x,Nil) else Nil }

  def main(args: Array[String]) {
    println(filter(List(1,2,3)){ x => x > 1 })
  }
}


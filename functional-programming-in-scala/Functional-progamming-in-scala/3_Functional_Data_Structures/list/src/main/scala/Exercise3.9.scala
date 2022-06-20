/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.9 - Compute the length of a list using foldRight
*
*
*/

package fpinscala.datastructures


import scala.annotation.tailrec

object FoldRightListLen {

  //
  //  This is a non tail recursive version of foldRight
  def foldRight[A,B](l: List[A], seed: B)(f: (A,B) => B): B = l match {
    case Nil => seed
    case Cons(h,tl) => f(h,foldRight(tl,seed)(f))
  }

  def length[A](l: List[A]) =
    foldRight(l,0){ (_,l) => l + 1 }

  def main(args: Array[String]) {
    val len = length(List(1,2,3,4,5,6,7,8,9,10))
    println(len)
  }
}


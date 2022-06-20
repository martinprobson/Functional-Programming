/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.8 - What happends when you pass Nil and Cons to foldRight?
*
*
*/

/*
 * This shows that foldRight replaces a Cons list with f(A,seed)
 *
 * for example: -
 *
 * foldRight(List(1,2,3),0)(add)
 *
 * translates to: -
 *  
 *   add(1,add(2,add(3,0)))
 *
 *   so in general: -
 *
 *   foldRight(List(A1,A2,A3),seed)(f) 
 *   is
 *   f(A1,f(A2,f(A3,seed)))
 *
 *
 */

package fpinscala.datastructures


import scala.annotation.tailrec

object FoldRightNilCons {

  //
  //  This is a non tail recursive version of foldRight
  //  non tail recursive foldRight
  def foldRight[A,B](l: List[A], seed: B)(f: (A,B) => B): B = l match {
    case Nil => seed
    case Cons(h,tl) => f(h,foldRight(tl,seed)(f))
  }

  def main(args: Array[String]) {
    val res1 = foldRight(List(1,2,3),Nil: List[Int]){ (x,y) => Cons(x,y) }
    assert(res1 == List(1,2,3))
    println(res1)
  }
}



/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.20 - Write a function flatMap that works like map except that the function
*                 given will return a list instead of a single result, and this list
*                 should be inserted into the final resulting list e.g.
*
*                 flatMap(List(1,2,3)){ i => List(i,i) }
*
*                 should result in
*
*                 List(1,1,2,2,3,3)
*
*/

package fpinscala.datastructures

object FlatMap {

  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = as match {
    case Nil => Nil
    case Cons(h,tl) => List.append(f(h),flatMap(tl)(f))
  }

}


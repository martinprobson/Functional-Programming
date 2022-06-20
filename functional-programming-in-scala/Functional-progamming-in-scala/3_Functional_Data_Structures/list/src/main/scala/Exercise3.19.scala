
/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.19 - Write a filter function that removes elements
*                 from a list unless they satisfy a given 
*                 predicate.
*
*/

package fpinscala.datastructures


import scala.annotation.tailrec

object Filter {

  //
  // filter_NTR - non-tail recursive
  //
  def filter_NTR[A](l: List[A])(f: A => Boolean): List[A] = 
    l match {
      case Nil => Nil
      case Cons(h,tl) => if (f(h)) Cons(h,filter_NTR(tl)(f))
                         else filter_NTR(tl)(f)
    }


  //
  // filter - tail recursive
  // written in terms of foldRight.
  //
  def filter[A](l: List[A])(f: A => Boolean): List[A] = 
    List.foldRight(l,Nil:List[A]){ (b,a) => 
                            if (f(a)) 
                                 Cons(a,b) 
                            else b }


  def main(args: Array[String]) {
    val l = filter_NTR(List(1,2,3,4,5,6,7,8,9,10)){ _ > 2}
    println(l)
    val l2 = filter(List(1,2,3,4,5,6,7,8,9,10)){ _ > 2}
    println(l2)

    val b = List.buildList(10000000){ a => a}
    // This will cause a stack overflow
    //val l3 = filter_NTR(b){ _ > 2}
    val l4 = filter(b){ _ > 2}

  }
}


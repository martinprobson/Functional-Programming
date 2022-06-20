
/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.16 - Write a pure function that transforms a list of integers 
*                 by adding one to each element.
*
* Exercise 3.17 - Write a function that turns each value in a List[Double] into a String.
*
* Exercise 3.18 - Write a generic map function.
*
*/

package fpinscala.datastructures


import scala.annotation.tailrec

object ListMap {

  //
  // map - non-tail recursive version
  //
  def map_NTR[A,B](l: List[A])(f: A => B): List[B] = l match {
    case Nil => Nil
    case Cons(h,tl) => Cons(f(h),map_NTR(tl)(f))
  }

  //
  // map - tail recursive version
  //
  // implementation in terms of foldRight
  //
  def map[A,B](l: List[A])(f: A => B): List[B] = 
      List.foldRight(l,Nil:List[B]){ (b,a) => Cons(f(a),b)}


  //
  // map - tail recursive version
  //
  // implementation with an accumulator
  // and reverse.
  //
  def map_TR[A,B](l: List[A])(f: A => B): List[B] =  {

    @tailrec
    def _map(accum: List[B],l: List[A])(f: A => B): List[B] = l match {
      case Nil         =>  accum
      case Cons(h,tl)  =>  _map(Cons(f(h),accum),tl)(f)
    }

    List.reverse(l) match {
      case Nil         =>  Nil
      case Cons(h,tl)  =>  _map(Cons(f(h),Nil),tl)(f)
      }
  }

  def main(args: Array[String]) {
    //
    // Exer 3.16
    val l = List(1,2,3,4,5,6,7,8,9,10)
    val exp = List(2,3,4,5,6,7,8,9,10,11)
    val f = (x:Int) => x + 1
    assert(map_NTR(l)(f) == map(l)(f))
    assert(map_TR(l)(f) == map(l)(f))

    //
    // Exer 3.17
    //
    val d: List[Double] = List(1,2,3,4,5,6,7,8,9,10)
    val actual = map(d){ _.toString }
    println(actual)

    val b = List.buildList(1000000){ a => a }
    map(b)(f)
    println(List.length(b))
  }
}


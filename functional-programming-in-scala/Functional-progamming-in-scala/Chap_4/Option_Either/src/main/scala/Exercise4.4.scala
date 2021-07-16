
package fpinscala.chap4
import scala.annotation.tailrec
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{Option => _, Either => _, List => _,Nil => _,_}

//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
// Exercise 4.4 - Write a function sequence that combines a list of Options into
//                one option containing a list of all the Some values in the original 
//                list. If the original list contains None even once the result of the 
//                function should be None.
//
//
object Sequence {
  def sequence[A](a: List[Option[A]]): Option[List[A]]  = a match {
    case Nil => Some(Nil)
    case Cons(h,tl) => h.flatMap( hh =>  sequence(tl) map (Cons(hh,_)))
  }

  /*
  * sequence written in terms of foldRight and map2
  */
  def sequence_FR[A](a: List[Option[A]]): Option[List[A]] = 
    List.foldRight(a,Some(Nil):Option[List[A]]) { (bb: Option[List[A]], aa: Option[A]) =>  Map2.map2(aa,bb) { Cons(_,_) } }


}

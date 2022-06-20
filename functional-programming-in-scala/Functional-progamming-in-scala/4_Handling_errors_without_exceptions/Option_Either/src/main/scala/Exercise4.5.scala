
package fpinscala.chap4
import scala.annotation.tailrec
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{Option => _, Either => _, List => _,Nil => _,_}

//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
// Exercise 4.5 - Implement the traverse function so that it only has to scan the input
//                list once.
//
//
object Traverse {

  /*
  *  Implement the traverse function so that it only has to scan the input list
  *  once.
  */
  def traverse[A,B](a: List[A])(f: A => Option[B]): Option[List[B]] = a match {
    case Nil => Some(Nil)
    case Cons(h,tl) => f(h).flatMap( hh =>  traverse(tl)(f) map (Cons(hh,_)))
  }

  /*
  * traverse implemented in terms of foldRight
  */
  def traverse_FR[A,B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    List.foldRight(a,Some(Nil):Option[List[B]]) { (bb: Option[List[B]], aa: A) => Map2.map2(f(aa),bb) { Cons(_,_) } }

  /*
  *  Sequence written to use traverse
  */
  def sequenceTraverse[A](a: List[Option[A]]): Option[List[A]]  = 
    traverse(a){ (aa) => aa }
}


package fpinscala.chap4
import scala.annotation.tailrec
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{Option => _, Either => _, List => _,Nil => _,_}

//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
// Exercise 4.7 - Implement the traverse and sequence for Either.
//
//
object EitherTraverse {

  /*
  *  Implement the traverse function so that it only has to scan the input list
  *  once.
  */
  def traverse[E, A,B](a: List[A])(f: A => Either[E,B]): Either[E, List[B]] = a match {
    case Nil => Right(Nil)
    case Cons(h,tl) => f(h).flatMap( hh =>  traverse(tl)(f) map (Cons(hh,_)))
  }

  /*
  *  Sequence written to use traverse
  */
  def sequence[E,A](a: List[Either[E,A]]): Either[E,List[A]]  = 
    traverse(a){ (aa) => aa }
}

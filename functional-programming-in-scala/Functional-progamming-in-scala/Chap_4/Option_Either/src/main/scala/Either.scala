
package fpinscala.chap4
//hide std library `Option` and `Either`, since we are writing our own in this chapter
import scala.{Option => _, Either => _, _}

//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
sealed trait Either[+E, +A] {

  /*
  * Exercise 4.6
  *
  * Apply f if the Right value of Either
  */
  def map[B](f: A => B): Either[E,B] = this match {
    case Left(e) => Left(e)
    case Right(x) => Right(f(x))
  }

  /*
  * Exercise 4.6
  *
  * Apply f if the Right value of Either
  */
  def flatMap[EE >: E,B](f: A => Either[EE,B]): Either[EE,B] = this match {
    case Left(e) => Left(e)
    case Right(x) => f(x)
  }

  /*
  * Exercise 4.6
  *
  * orElse 
  */
  def orElse[EE >: E, B >: A](b: => Either[EE,B]): Either[EE,B] = this match {
    case Left(e) => b
    case Right(x) => Right(x)
  }

  /*
  * Exercise 4.6
  *
  * map2 with Either's
  */
  def map2[EE >: E,B,C](b: Either[EE,B])(f: (A,B) => C): Either[EE,C] =
    this.flatMap(p1 => b.map( p2 => f(p1,p2)))

//  def map2[A,B,C](x: Option[A], y: Option[B])(f: (A,B) => C): Option[C] = 
//    x.flatMap(p1 => y.map( p2 => f(p1,p2)))
}

case class Left[+E](value: E) extends Either[E,Nothing]
case class Right[+A](value: A) extends Either[Nothing,A]


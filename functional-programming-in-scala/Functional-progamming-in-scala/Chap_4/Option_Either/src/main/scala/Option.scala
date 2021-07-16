
package fpinscala.chap4
//hide std library `Option` and `Either`, since we are writing our own in this chapter
import scala.{Option => _, Either => _, _}
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grizzled.slf4j.Logging

//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
sealed trait Option[+A] extends Logging {

  /*
  * Exercise 4.1
  *
  * Apply f if the Option is not None
  */
  def map[B](f: A => B): Option[B] = 
    map_trace(f)

  private def _map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(x) => Some(f(x))
  }

  /*
  * Exercise 4.1
  *
  * Apply f if the Option is not None
  * Debug version for tracing execution
  */
  private def map_trace[B](f: A => B): Option[B] = {
    trace(s"In map: $this")
    val res = _map(f)
    trace(s"map is returning: $res")
    res
  }

  /*
  * Exercise 4.1
  *
  * Apply f, which may fail if the Option is not None
  *
  * Note:
  * The result of the map is either Some(Some(x)) if
  * f returns Some or
  * Some(None) if f returns None.
  * The getOrElse unpacks Some(Some(x)) to Some(x)
  * or None.
  */
  def flatMap[B](f: A => Option[B]): Option[B] =
    flatMap_trace(f)

  private def _flatMap[B](f: A => Option[B]): Option[B] = 
    map(f) getOrElse None

  private def flatMap_trace[B](f: A => Option[B]): Option[B] = {
    trace(s"In flatMap: $this")
    val res = this match {
      case None => None
      case Some(x) => f(x)
    }
    trace(s"flatMap is returning: $res")
    res
  }

  /*
  * Exercise 4.1
  *
  * getOrElse - returns the result inside the Some case or 
  *             default if None
  */
  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(x) => x
  }

  /*
  * Exercise 4.1
  *
  * orElse -    returns the first option if it is defined
  *             otherwise returns the second option.
  */
  def orElse[B >: A](op: => Option[B]): Option[B] = 
    this map(Some(_)) getOrElse op


  /*
  * Exercise 4.1
  *
  * filter -    Convert Some to None if value does not
  *             satisfy f.
  */
  def filter(f: A => Boolean): Option[A] = this match {
    case None => None
    case Some(x) => if (f(this.get)) this else None
  }
//    if (isEmpty || !f(this.get)) None else this 

  def get: A

}

case class Some[+A](get: A) extends Option[A]  

case object None extends Option[Nothing] {
  override def get = throw new NoSuchElementException("None.get")
}

object Option {

  /*
  * Exercise 4.1
  *
  * Apply f if the Option is not None
  */
  def mapO[A,B](a: Option[A])(f: A => B): Option[B] = a match {
    case None => None
    case Some(x) => Some(f(x))
  }

  def flatMapO[A,B](a: Option[A])(f: A => Option[B]): Option[B] = 
    Option.mapO(a)(f) getOrElse None

  /*
  * Try function - convert an exception to an Option
  */
  def Try[A](a: => A): Option[A] =
    try Some(a)
    catch { case e: Exception => None }
}

 

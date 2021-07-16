
package fpinscala.chap4
//hide std library `Option` and `Either`, since we are writing our own in this chapter
import fpinscala.datastructures._
import scala.{Option => _, Either => _, _}
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grizzled.slf4j.Logging


//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
//
// Exercise 4.3 - Implement generic function map2 that combines two Option
//                values using a binary function. If either Option is None then
//                the return value is too.
//
//                *Note* this has trace logging included so I can trace program
//                execution.
//
//
object Map2 extends Logging {
  /*
  * Follow the type signatures of flatMap and map on Option: -
  *
  * flatMap is used when the function can fail (i.e. returns an Option - A => Option[B])
  * map is used to map a 'straight' function call A => B
  *
  * Reading the code below from inside to out: -
  *   p2 => f(p1,p2) - This is the A => B map function, if the code has reached this point,
  *   we know that the f(p1,p2) function call will not fail (as we can already dealt with 
  *   the possibility of failure using the flatMap on x and the map on y.
  *   We are using map here because p2 => f(p1,p2) returns a B.
  *
  *   y.map(p2 => f(p1,p2)) - This function returns an Option (because y is an Option)
  *   therefore we have to use flatMap because the function signature is
  *   A => Option[B].
  *
  *   x.flatMap - This takes care of the x Option.
  *
  *   Note that this is similar to function parameter currying with the added complication
  *   of dealing with Option : -
  *
  *  def curry[A,B,C](f: (A,B) => C): A => (B => C) =
  *   (a: A) =>  (b: B) =>  f(a,b)
  */
  def map2[A,B,C](x: Option[A], y: Option[B])(f: (A,B) => C): Option[C] = {
    trace(s"In map2 x = $x y = $y")
    val res = x.flatMap(p1 => y.map( p2 => f(p1,p2)))
    trace(s"map2 is returning: $res")
    res
  }
  
}

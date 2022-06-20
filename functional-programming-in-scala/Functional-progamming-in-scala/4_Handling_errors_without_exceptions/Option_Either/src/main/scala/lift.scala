package fpinscala.chap4
//hide std library `Option` and `Either`, since we are writing our own in this chapter
import scala.{Option => _, Either => _, _}

//
// Functional Programming in Scala
// Chapter 4 - Handling errors without exceptions
//
// This is an implementation of the 'lift' function that allows us to lift
// ordinary functions to become functions that operate on Option.
// Note that the original function we are lifting (in this case math.abs) 
// does not need to know anything about Option, we are simply wrapping the 
// function via the lift function.
//
object Lift extends App {
  /*
  * This is the definition of the lift function as taken from page 56 of 
  * the book.
  */
  def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f

  /* 
  * I found the defintion above hard to understand at first, so a 
  * more explicit version is defined below for pedagogical purpose
  */
  def lift2[A,B](f: A => B): Option[A] => Option[B] = { (a: Option[A]) => 
    a match { 
      case None => None
      case Some(x) => Some(f(x))
    }
  }
    

  /*
  * Test
  */
  val liftAbs = lift(math.abs)
  val lift2Abs = lift2(math.abs)

  println(liftAbs(Some(-1)))
  println(lift2Abs(Some(-1)))
}

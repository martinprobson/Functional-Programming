package cats_monads

import cats.implicits._
import cats._
import cats.data.Writer

object CatsWriter extends App {

  type Logged[A] = Writer[Vector[String],A]

  def factorial(n: Int): Logged[BigInt] = {
    val a: Logged[BigInt] = if (n == 1) BigInt(1).pure[Logged] else factorial(n - 1).map(_*n)
    a.flatMap{ ans => Vector(s"factorial $n $ans").tell.map{ _ => ans}}
  }
  /*
  OR using for comprehension
   */
  def _factorial(n: Int): Logged[BigInt] = {
    for {
      ans <- if (n == 1) BigInt(1).pure[Logged] else _factorial(n - 1).map(_*n)
      _ <- Vector(s"factorial $n $ans").tell
    } yield ans
  }

  val (log,answer) = _factorial(5000).run
  println(log)
  println(answer)
}

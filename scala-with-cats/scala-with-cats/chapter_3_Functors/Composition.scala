import cats._ 
import cats.implicits._ 

object Composition extends App {

  val func1: Int => Double = (x: Int) => x.toDouble

  val func2: Double => Double = (y: Double) => y * 2

  println(func1.map(func2)(10))

  val liftedFunc = Functor[Option]lift( (x: Int) => x + 1)
  println(liftedFunc(Some(1)))
}



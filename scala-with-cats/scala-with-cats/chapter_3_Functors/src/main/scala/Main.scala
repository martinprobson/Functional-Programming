
import cats.instances.function._ // for Functor
import cats.syntax.functor._     // for map
import cats.Id
import cats.Monad

object Main extends App {

  val a = Monad[Id].pure(2)
  val b = Monad[Id].map(a){_*2}
  println(s"b = $b")

  //
  // Function composition using map.
  //
  def map[X,A,B](f1: X => A)(f2: A => B): X => B = x => f2(f1(x))

  val f1: Int => Long = (x: Int) => (x+1).toLong
  val f2: Long => String = (x: Long) => s"$x Long"

  val res = (f1 map f2)(100)
  println(res)
  val res3 = f1 andThen f2
  println(res3(100))
  val res4 = f2 compose f1
  println(res4(100))
  val res2 = map(f1)(f2)(100)
  println(res2)
  List(1,2,3,4).map(_ + 1)

}


import cats.Monoid
import cats.instances.int._ // for Monoid
//import cats.instances.string._ // for Monoid
//import cats.instances.option._ // for Monoid

object Main extends App {

  // This is Scala's context bound syntax and is shorthand for
  // def add(items: List[A])(implicit m: Monoid[A]): A =
  def add[A: Monoid](items: List[A]): A =
    items.foldLeft(Monoid[A].empty)(Monoid[A].combine)

  println(add(List(1,2,3,4,5,6,7,8,9,10)))

}

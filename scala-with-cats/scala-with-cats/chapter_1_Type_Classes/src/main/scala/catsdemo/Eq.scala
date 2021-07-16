package catsdemo

import cats.implicits._
import common.{Cat, Person}

object Eq extends App {

  val cat1 = Cat("Garfield", 38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]
  println(s"cat1 === cat2 : ${cat1.show} === ${cat2.show} : ${cat1 === cat2}")
  println(s"optionCat1 === Option.empty[common.Cat] : ${optionCat1.show} === ${optionCat2.show} : ${optionCat1 === optionCat2}")

  val p1 = Person("Garfield", "garfield@gmail")
  val p2 = Person("Heathcliff","heathcliff@yahoo")
  val optionP1 = Option(p1)
  val optionP2 = Option.empty[Person]
  println(s"p1 === p2 : ${p1.show} === ${p2.show} : ${p1 === p2}")
  println(s"p1 === Option.empty[common.Person] : ${optionP1.show} === ${optionP2.show} : ${optionP1 === optionP2}")
  println(s"p1 === p1 : ${p1.show} === ${p1.show} : ${p1 === p1}")
}

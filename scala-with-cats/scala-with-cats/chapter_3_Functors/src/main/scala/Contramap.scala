trait Printable[A] {
  self =>
  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    (value: B) => self.format(func(value))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)

    def print(implicit p: Printable[A]): Unit =
      println(p.format(value))
  }
}

object PrintableInstances {
  implicit val booleanPrintable: Printable[Boolean] = (value: Boolean) => if (value) "yes" else "no"
  implicit val stringPrintable: Printable[String] = (value: String) => s"String: $value"
  implicit val intPrintable: Printable[Int] = (value: Int) => s"Int $value"
  implicit val catPrintable: Printable[Cat] = (value: Cat) => s"${value.name} is a ${value.age} year old, ${value.colour} cat."
  /*
  Define boxPrintable with a contramap.
  def contramap[B](func: B => A): Printable[B] =
    (value: B) => self.format(func(value))

  contramap[Box[A]](func: Box[A] => A): Printable[Box[A]] =
    (value: Box[A]) => self.format(func(value))
   */
  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] = p.contramap(_.value)
}

final case class Box[A](value: A)
final case class Cat(name: String, age: Int, colour: String)

import PrintableSyntax._
import PrintableInstances._

object ContraMap extends App {
  Cat("Fred",31,"White").print
  "Martin".print
  10.print
  Box(10).print
  Box("Martin").print
  Box(Cat("Boc",100,"Ginger")).print
  Box(true).print
}

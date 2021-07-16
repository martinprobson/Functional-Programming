package catsdemo

import common.{Cat, Person}
//
// Specific imports (normally import cats.implicits._ does all this
//
//import cats.implicits._
import cats.syntax.show._
import cats.instances.option._
import cats.instances.int._

object Show extends App {

  println(Cat("Puskin",5,"Gray").show)

  println(Option(Cat("Mad cat",8,"white")).show)
  val s: Option[Cat] = Some(Cat("Mad cat",8,"white"))
  println(s.show)
  println(123.show)
  println(Person.personShow.show(Person("Martin","gmail")))
  println(Person("Martin","gmail").show)
}


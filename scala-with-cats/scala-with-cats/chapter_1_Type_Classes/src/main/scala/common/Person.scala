package common

import cats._
import cats.implicits._

final case class Person(name: String, email: String)

object Person {

  implicit val personShow: Show[Person] =
    Show.show(p => s"common.Person name: ${p.name} email: ${p.email}")

  implicit val personEq: Eq[Person] =
    Eq.instance[Person] { (p1, p2) =>
      p1.name === p2.name &&
      p1.email === p2.email
    }
}

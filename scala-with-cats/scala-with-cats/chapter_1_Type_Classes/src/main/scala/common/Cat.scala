package common

import cats._
import cats.implicits._

final case class Cat(name: String, age: Int, colour: String)

object Cat {

  implicit val catShow: Show[Cat] =
    Show.show(c => s"${c.name} is a ${c.age} year old ${c.colour} cat.")

  implicit val catEq: Eq[Cat] =
    Eq.instance[Cat] { (c1, c2) =>
      c1.name === c2.name &&
        c1.age === c2.age &&
        c1.colour === c2.colour
    }
}

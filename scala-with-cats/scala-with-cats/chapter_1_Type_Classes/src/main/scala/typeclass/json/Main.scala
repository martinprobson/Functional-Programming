package typeclass.json

import common.Person

object Main extends App {
  val s = Json.toJson(Person("Martin","martin@gmail"))
  println(s)
  val n = Json.toJson(Integer.valueOf(10))
  println(n)
  val n2 = Json.toJson(10)
  println(n2)
  val d = Json.toJson(10.89)
  println(d)
}

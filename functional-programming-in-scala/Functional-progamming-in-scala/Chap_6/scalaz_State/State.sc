import scalaz._
import Scalaz._
import WordCount._

val m1 = State { s: String => (s, s.size) }
m1.run(_)
def repeat(num: Int): State[String, Unit] = State { s: String => (s * num, ()) }


val m3 = State{ s: String => (s,s.size)}.flatMap( (i: Int) => State { s2:String => (s2 * i, ()) })

val m2 = m1.flatMap(repeat(_))

m1.map( i => i * 2).flatMap(repeat).run("hello")

m2("MartinRobson")
m3("MAR")

State.get[String].flatMap( (s:String) => repeat(s.size)).run("Martin")

get[String]                               // "Dog" ===> State("Dog","Dog")
  .flatMap({ s0 => put(s0 * s0.size) })   // State("Dog","Dog") ====> State("DogDogDog",())
  .flatMap({ _ => get[String]})           // State("DogDogDog","DogDogDog")
  .map({ s1 => s1.size })                 // State("DogDogDog",9)
  .run("Dog")

get("Dog")

get[String].flatMap({ s0 => put(s0 * s0.size)}).run("Dog")

get[String]
  .flatMap({ s0 => put(s0 * s0.size)})
  .flatMap({ _ => get[String]})
  .run("Dog")

get[String]
  .flatMap({ s0 => put(s0 * s0.size)})
  .flatMap({ _ => get[String]})
  .map({ s1 => s1.size })
  .run("Dog")


val m = for {
  s0 <- get[String]
  _  <- modify( (s:String)  => s * s.size)
  s1 <- get[String]

} yield s1.size

m.run("Dog")
val state1 = State( (s: String) => (s,s.reverse))
state1.run("Fred")
State.modify { s: String => s.reverse }

val tst = modify { s: String => s.reverse }.run("Martin")
//List(1,2,3).foldRight

object FibNaïve {
  def fibnaïve(n: BigInt): BigInt =
    if(n <= 1)
      n
    else {
      val r = fibnaïve(n - 1)
      val s = fibnaïve(n - 2)
      r + s
    }
}

//FibNaïve.fibnaïve(40)

object FibMemo1 {
  type Memo = Map[BigInt, BigInt]

  def fibmemo1(n: BigInt): BigInt = {
    def fibmemoR(z: BigInt, memo: Memo): (BigInt, Memo) =
      if(z <= 1)
        (z, memo)
      else memo get z match {
        case None => {
          val (r, memo0) = fibmemoR(z - 1, memo)
          val (s, memo1) = fibmemoR(z - 2, memo0)
          (r + s, memo1)
        }
        case Some(v) => (v, memo)
      }

    fibmemoR(n, Map())._1
  }
}

//FibMemo1.fibmemo1(40)

List.fill(10)("gjhgjhgjhg")

import java.util.Random
package fpinscala.chap6

import scala.annotation.tailrec

object Main extends App {

  val rng = SimpleRNG(Int.MinValue)
  val (n,rng1)  = Exercise61.nonNegativeInt(rng)
  val (n2,rng2) = Exercise61.nonNegativeInt(rng1)
  val (n3,rng3) = Exercise61.nonNegativeInt(rng2)
  val (n4,rng4) = Exercise61.nonNegativeInt(rng3)
  val (n5,rng5) = Exercise61.nonNegativeInt(rng4)
  println(s"$n $n2 $n3 $n4 $n5")

//  val l: List[Rand[Int]] = List(SimpleRNG(1),SimpleRNG(2),SimpleRNG(3))
  










}

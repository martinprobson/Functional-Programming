package net.martinprobson.cats

import cats.data.State

object Main extends App {

  val step1 = State[Int, String] { num =>
    val ans = num + 1
    (ans, s"Result of step1: $ans")
  }

  val step2 = State[Int, String] { num =>
    val ans = num * 10
    (ans, s"Result of step2: $ans")
  }

  val step3 = State[Int, String] { num =>
    val ans = num * 2
    (ans, s"Result of step3: $ans")
  }

  val both = for {
    a <- step1
    b <- step2
    c <- step3
  } yield (a, b, c)

  val (state, result) = both.run(20).value
  println(s"Result state: $state result: $result")

  val both2 = step1.flatMap{ a => step2.flatMap{ b => step3.map{ c => (a, b, c)}}}

  val (state2, result2) = both2.run(20).value
  println(s"Result state2: $state2 result2: $result2")

  // Ignore intermediate results (the last map is superfluous
  //val both3 = step1.flatMap{ _ => step2.flatMap{ _ => step3.map{ c => c}}}
  val both3 = step1.flatMap{ _ => step2.flatMap{ _ => step3}}

  // Get the result, ignore the state
  val result3 = both3.runA(20).value
  println(s"Result: $result3")
  // Get the state, ignore the result
  val state3 = both3.runS(20).value
  println(s"State: $state3")
}

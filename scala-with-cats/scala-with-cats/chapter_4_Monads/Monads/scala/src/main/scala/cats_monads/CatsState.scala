package cats_monads

import cats.implicits._
import cats.data.State

object CatsState extends App {

  //TODO How does this work?
  val a = 1.pure[Option]

  type CalcState[A] = State[List[Int],A]

  def operator( f: (Int,Int) => Int): CalcState[Int] = State[List[Int],Int] { state =>
    val ans = f(state.head,state.tail.head)
    (ans :: state.tail.tail,ans)
  }

  def operand(i : Int): CalcState[Int] = State[List[Int],Int] { state =>
    (i :: state, i)
  }
  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num.toInt)
    }

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(0.pure[CalcState]){ (state,sym) =>
      state.flatMap{ _ => evalOne(sym)}
    }

  def evalInput(input: String): Int =
    evalAll(input.split(" ").toList).runA(Nil).value

  val r = for {
    _ <- evalOne("1")
    _ <- evalOne("2")
    _ <- evalOne("+")
    _ <- evalOne("10")
    _ <- evalOne("100")
    _ <- evalOne("*")
    a <- evalOne("*")
  } yield a
  val res = r.run(List[Int]()).value
  println(s"res = $res")
  val r4 = evalOne("1")
    .flatMap(_ => evalOne("2")
    .flatMap(_ => evalOne("+")))
  val res4 = r4.run(List[Int]()).value
  println(s"res4 = $res4")

  val res2 = evalAll(List("1","2","+","10","100","*","*")).run(List[Int]()).value
  println(res2)

  val r2 = for {
    _ <- evalAll(List("1","2","+","10","100","*","*"))
    _ <- evalAll(List("1","2","+","10","100","*","*"))
    _ <- evalAll(List("1","2","+","10","100","*","*"))
    _ <- evalAll(List("1","2","+","10","100","*","*"))
    _ <- evalAll(List("1","2","+","10","100","*","*"))
    _ <- evalOne("*")
    _ <- evalOne("*")
    _ <- evalOne("*")
    a <- evalOne("*")
  } yield a
  val res3 = r2.run(List[Int]()).value
  println(res3)
  println(evalInput("1 2 + 3 4 + *"))
}
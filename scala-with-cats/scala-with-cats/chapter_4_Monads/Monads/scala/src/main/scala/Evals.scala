object Evals extends App {

  def eval(t: Term): Int = t match {
    case Con(a) => a
    case Div(t, u) => eval(t) / eval(u)
  }

  def evalMonad[F[_] : Monad](t : Term)(m: Monad[F]): F[Int] = t match {
    case Con(a) => m.pure(a)
    case Div(t,u) => m.flatMap(evalMonad(t)(m))( a =>
      m.flatMap(evalMonad(u)(m))( b => m.pure(a / b)))
  }

  def evalWithExc(t: Term): Maybe[Int] = t match {
    case Con(a) => Return(a)
    case Div(t, u) => evalWithExc(t) match {
      case Raise(e) => Raise(e)
      case Return(t1) => evalWithExc(u) match {
        case Raise(e) => Raise(e)
        case Return(u1) => if (u1 == 0) Raise("Divide by zero") else Return(t1 / u1)
      }
    }
  }

  import State._
  def evalWithState(t: Term)(s: M2[Int]): M2[Int] = (t,s) match {
    case (Con(a),_) => y => (a,y)
    case (Div(t,u),x) =>
      val (t1: Int,s1: Int) = evalWithState(t)(x)(0)
      val (u2: Int,s2: Int) = evalWithState(u)(x)(0)
      _ => (t1 / u2,s1 + s2 + 1)
  }

  def line(t: Term, i: Int): String =
    "eval (" + t + ") " + i + "\n"

  import Output._
  def evalWithOutput(t: Term): Output[Int] = t match {
    case Con(a) => (line(Con(a),a),a)
    case Div(t,u) =>
      val (x: String,a: Int) = evalWithOutput(t)
      val (y: String,b: Int) = evalWithOutput(u)
      (x + y + line(Div(t,u), a/b),a/b)
  }
  val answer = Div(Div(Con(1972),Con(2)),Con(23))
  val answer2 = Div(Div(answer,answer),Div(answer,answer))
  val err = Div(Con(1),Con(0))

  println(s"eval(answer) = ${eval(answer)}")
  //println(s"eval(err) = ${eval(err)}")
  println(s"evalWithExc(answer) ${evalWithExc(answer)}")
  println(s"evalWithExc(err) ${evalWithExc(err)}")
  import Monad._
  println(s"evalMonad(answer)(maybeMonid) ${evalMonad(answer)(maybeMonad)}")
  //TODO This throws an exception. How to fix?
  //println(s"evalMonad(err)(maybeMonid) ${evalMonad(err)(maybeMonid)}")
  //println(s"evalWithExc(err) = ${evalWithExc(err)}")
  println(s"evalMonad(answer)(idMonad) ${evalMonad(answer)(idMonad)}")

  println(s"evalWithState(Con(1))(unit(1))(0) ${evalWithState(Con(1))(unit(1))(0)}")
  println(s"evalWithState(Con(10))(unit(1000000))(0) ${evalWithState(Con(10))(unit(1000000))(0)}")
  println(s"evalWithState(Div(Con(20),Con(10)))(unit(1000000))(0) ${evalWithState(Div(Con(20),Con(10)))(unit(1000000))(0)}")
  println(s"evalWithState(answer)(unit(0))(0) ${evalWithState(answer)(unit(0))(0)}")
  println(s"evalWithState(answer2)(unit(0))(0) ${evalWithState(answer2)(unit(0))(0)}")
  println(s"evalWithOutput(answer) ${evalWithOutput(answer)}")
  println(s"evalWithOutput(answer2) ${evalWithOutput(answer2)}")

  //evalWithState(err)(unit(0))(0)
  //evalWithState(answer)(unit(1))(0)

}

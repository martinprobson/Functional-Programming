/**
* Model traffic lights using scalaz State
* From: http://timperrett.com/2013/11/25/understanding-state-monad/
*/


sealed trait Aspect
case object Green extends Aspect
case object Amber extends Aspect
case object Red   extends Aspect

sealed trait Mode
case object Off      extends Mode
case object Flashing extends Mode
case object Solid    extends Mode

// represents the actual display set: must be enabled before 
// it can be used. 
case class Signal(
    isOperational: Boolean, 
    display: Map[Aspect, Mode])

object Signal {
  import scalaz.syntax.state._
  import scalaz.State, State._

  // just a lil' bit of sugar to use later on.
  type ->[A,B] = (A,B)

  // convenience alias as all state ops here will deal
  // with signal state.
  type SignalState[A] = State[Signal,A]

  // dysfunctional lights revert to their flashing 
  // red lights to act as a stop sign to keep folks safe.
  val default = Signal(
    isOperational = false, 
    display = Map(Red -> Flashing, Amber -> Off, Green -> Off))

  def enable: State[Signal, Boolean] = 
    for {
      a <- init						// a is superfluous here
      _ <- modify((s: Signal) => s.copy(isOperational = true))
      r <- get
    } yield r.isOperational


  def enable2: State[Signal,Boolean] =
    init.flatMap( a => modify(  (s:Signal) => s.copy(isOperational = true)) // and here
        .flatMap( _ => get.map( r => r.isOperational)))

  def change(seq: Aspect -> Mode*): State[Signal, Map[Aspect, Mode]] = 
    for {
      m <- init
      _ <- modify(display(seq))
      s <- get
    } yield s.display

  // FIXME: requires domain logic to prevent invalid state changes
  // or apply any other domain rules that might be needed. 
  // I leave that as an exercise for the reader.
  def display(seq: Seq[(Aspect,Mode)]): Signal => Signal = signal =>
    if(signal.isOperational)
      signal.copy(display = signal.display ++ seq.toMap)
    else default

  // common states the signal can be in.
  def halt  = change((Red,Solid), Amber -> Off,   Green -> Off)
  def ready = change(Red -> Solid, Amber -> Solid, Green -> Off)
  def go    = change(Red -> Off,   Amber -> Off,   Green -> Solid)
  def slow  = change(Red -> Off,   Amber -> Solid, Green -> Off)

}

object TrafficLightExample extends App {
  import scalaz.State.{get => current}
  import Signal._
  val program = for {
    _  <- enable
    r0 <- current // debuggin
    _  <- halt
    r1 <- current // debuggin
    _  <- ready 
    r2 <- current // debuggin
    _  <- go
    r3 <- current // debuggin
    _  <- slow
    r4 <- current
  } yield r0 :: r1 :: r2 :: r3 :: r4 :: Nil

  program.eval(default).zipWithIndex.foreach { case (v,i) =>
    println(s"r$i - $v")
  }
}

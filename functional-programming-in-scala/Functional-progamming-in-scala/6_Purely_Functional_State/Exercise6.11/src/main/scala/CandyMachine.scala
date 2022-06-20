/**
* Exercise 6.11 - Implement a finite state automaton that models a simple candy dispenser.
*                 The machine has two types of input: you can insert a coin, or you can
*                 turn a knob to dispense candy. It can be in one of two states: locked 
*                 or unlocked. It also tracks how many candies are left and how many coins
*                 it contains.
*/
sealed trait Input

case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)


import State._

object Machine {

  def init: State[Machine,(Int,Int)] =
    State( m => ( (0,0),m ) )

  def change( currentState: State[Machine,(Int,Int)], input: Input): State[Machine,(Int,Int)] = 
    for {
      a <- currentState
      _ <- modify(action(input))
      m <- get
    } yield (m.candies,m.coins)

//  This is the flatMap/Map version of the for comprehension above
//  def change( currentState: State[Machine,(Int,Int)], input: Input): State[Machine,(Int,Int)] = 
//    currentState.flatMap { _ =>  modify(action(input)) }.flatMap{ _ => get.map{ m => (m.candies,m.coins) }}

  def action(input: Input): Machine => Machine = 
    m => (m,input) match {
      case (Machine(_,0,_),_)        => m                                             /* Out of candy - no state change */
      case (Machine(false,_,_),Coin) => m                                             /* Coin - machine already unlocked - no state change */
      case (Machine(true,_,_),Turn)  => m                                             /* Turn - machine locked - no action */
      case (Machine(true,_,_),Coin)  => m.copy(locked = false, coins = m.coins +1)    /* Coin - Insert coin unlock machine, increment coins count */
      case (Machine(false,_,_),Turn) => m.copy(locked = true, candies = m.candies -1) /* Turn - dispence candy */
    }

  def simulateMachine(inputs: List[Input]): State[Machine, (Int,Int)] =
    inputs.foldLeft(init){ change }
}

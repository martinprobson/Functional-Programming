
package fpinscala.chap6

import Exercise61.nonNegativeInt

/**
* Exercise 6.2 - Write a function to generate a Double between 0 and 1, not including 1.
*
*/
object Exercise62 {

  def double(rng: RNG): (Double,RNG) = {
    val (i,nextRNG) = nonNegativeInt(rng)
    ( i / (Int.MaxValue.toDouble + 1),nextRNG)
  }
}


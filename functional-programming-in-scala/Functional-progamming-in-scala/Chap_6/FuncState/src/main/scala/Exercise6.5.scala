

package fpinscala.chap6

import Exercise61.nonNegativeInt
import Rng._

/**
* Exercise 6.5 - Use map to implement double.
*
*/
object Exercise65 {

  def double: Rand[Double] =
    Rng.map(nonNegativeInt)( _ / (Int.MaxValue.toDouble + 1))
}


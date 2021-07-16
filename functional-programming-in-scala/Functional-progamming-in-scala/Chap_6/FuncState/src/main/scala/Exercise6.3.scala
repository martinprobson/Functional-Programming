


package fpinscala.chap6

import Exercise61.nonNegativeInt
import Exercise62.double


/**
* Exercise 6.3 - Write functions to generate an (Int, Double) pair, a (Double, Int) pair,
*                and a (Double, Double, Double) 3-tuple.
*
*/
object Exercise63 {

  def intDouble(rng: RNG): ((Int,Double),RNG) = {
    val (i,r) = nonNegativeInt(rng)
    val (d,r2) = double(r)
    ((i,d),r2)
  }

  def doubleInt(rng: RNG): ((Double,Int),RNG) = {
    val ((i,d),r) = intDouble(rng)
    ((d,i),r)
  }

  def double3(rng: RNG): ((Double,Double,Double),RNG) = {
    val (d1,r1) = double(rng)
    val (d2,r2) = double(r1)
    val (d3,r3) = double(r2)

    ((d1,d2,d3),r3)
  }
}



package fpinscala.chap6

import Rng.int

/**
* Exercise 6.4 - Write a function to generate a list of random integers.
*
*/
object Exercise64 {

  def ints(count: Int)(rng: RNG): (List[Int],RNG) = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[Int]): (List[Int],RNG) = 
      if (n > 0) {
        val (r,rg) = int(rng)
        _gen(n-1,rg,r :: l) }
      else (l,rng)

    _gen(count,rng,Nil) 
  }

}


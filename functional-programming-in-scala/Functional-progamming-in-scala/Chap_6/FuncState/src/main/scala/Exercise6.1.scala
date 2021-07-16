package fpinscala.chap6

/**
* Exercise 6.1 - Write a function that uses RNG.nextInt to generate a random integer 
*                between 0 and Int.MaxValue (inclusive). Make sure to handle the corner
*                case when nextInt returns Int.MinValue, which doesn't have a non-negative
*                counterpart.
*/
object Exercise61 {
  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (n,nextRNG)  = rng.nextInt
    val r = Math.abs(if (n == Int.MinValue) n+1 else n)
    (r,nextRNG)
  }
}


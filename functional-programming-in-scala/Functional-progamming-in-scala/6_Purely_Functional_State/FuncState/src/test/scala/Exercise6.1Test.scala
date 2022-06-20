

package fpinscala.chap6

import org.scalatest.FunSuite

class Exercise61Test extends FunSuite {

  def genNonNegativeInts(n: Int,rng: RNG): List[Int] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[Int]): List[Int] = 
      if (n > 0) {
        val (r,rg) = Exercise61.nonNegativeInt(rng)
        _gen(n-1,rg,r :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  test("1: nonNegativeInt") {
    assert(genNonNegativeInts(100,SimpleRNG(100)) === genNonNegativeInts(100,SimpleRNG(100)))
  }

  test("2: nonNegativeInt") {
    assert(genNonNegativeInts(10000,SimpleRNG(1)).exists(_<0) === false)
    assert(genNonNegativeInts(10000,SimpleRNG(-11)).exists(_<0) === false)
  }
}


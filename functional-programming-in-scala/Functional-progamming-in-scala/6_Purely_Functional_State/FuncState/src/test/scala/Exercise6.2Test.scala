
package fpinscala.chap6

import org.scalatest.FunSuite

class Exercise62Test extends FunSuite {

  import Exercise62Test.genDoubles

  test("1: double") {
    assert(genDoubles(100,SimpleRNG(100)) === genDoubles(100,SimpleRNG(100)))
  }

  test("2: double") {
    assert(genDoubles(100000,SimpleRNG(1)).exists( (a) => (a >= 0.0 && a < 1)) === true)
    assert(genDoubles(100000,SimpleRNG(111)).exists( (a) => (a >= 0.0 && a < 1)) === true)
  }
}

object Exercise62Test {

  def genDoubles(n: Int,rng: RNG): List[Double] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[Double]): List[Double] = 
      if (n > 0) {
        val (r,rg) = Exercise62.double(rng)
        _gen(n-1,rg,r :: l) }
      else l

    _gen(n,rng,Nil) 
  }
}

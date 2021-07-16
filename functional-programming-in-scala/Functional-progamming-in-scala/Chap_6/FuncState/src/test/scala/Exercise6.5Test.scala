

package fpinscala.chap6

import org.scalatest.FunSuite
import fpinscala.chap6.Exercise62Test.{genDoubles => Exercise62GenDoubles}

class Exercise65Test extends FunSuite {

  def genDoubles(n: Int,rng: RNG): List[Double] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[Double]): List[Double] = 
      if (n > 0) {
        val (r,rg) = Exercise65.double(rng)
        _gen(n-1,rg,r :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  test("1: Exercise65.double") {
    assert(genDoubles(100,SimpleRNG(100)) === genDoubles(100,SimpleRNG(100)))
  }

  test("2: Exercise65.double") {
    assert(genDoubles(100000,SimpleRNG(1)).exists( (a) => (a >= 0.0 && a < 1)) === true)
    assert(genDoubles(100000,SimpleRNG(111)).exists( (a) => (a >= 0.0 && a < 1)) === true)
  }

  test("3: Exercise65.double == Exercise62.double (for same seed)") {
    assert(genDoubles(100,SimpleRNG(100)) === Exercise62GenDoubles(100,SimpleRNG(100)))
  }
}


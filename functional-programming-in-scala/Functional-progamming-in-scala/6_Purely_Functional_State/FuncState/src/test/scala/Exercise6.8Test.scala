


package fpinscala.chap6

import org.scalatest.FunSuite

class Exercise68Test extends FunSuite {

  test("1: nonNegativeInt Test - flatMap") {
    //
    // Generate a sequence of nonNegativeLessThan's and check the results
    // match our criteria.
    val (l,rng) = Rng.sequence( List.fill(10000)( Rng.nonNegativeLessThan(100) ) )(SimpleRNG(1))
    assert( l.filter( a => a >= 0 && a < 100).length == 10000 && rng.isInstanceOf[RNG])
  }
}


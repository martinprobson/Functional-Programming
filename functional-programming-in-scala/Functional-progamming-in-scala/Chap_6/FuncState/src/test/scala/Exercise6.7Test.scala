


package fpinscala.chap6

import org.scalatest.FunSuite
import Exercise64.ints

class Exercise67Test extends FunSuite {

  def genDoubles(n: Int,rng: RNG): List[Double] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[Double]): List[Double] = 
      if (n > 0) {
        val (r,rg) = Exercise65.double(rng)
        _gen(n-1,rg,r :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  test("1: sequence non-empty list") {
    val rands = List(NegativeInt.nonNegativeEven(_),NegativeInt.nonNegativeEven(_),NegativeInt.negativeInt(_))
    val res = Rng.sequence(rands)(SimpleRNG(9))
    val exp = (List(2079917648, 1805194354, -3462740),SimpleRNG(145165493683098L))
    assert(res === exp)
  }

  test("2: sequence empty list") {
    val res = Rng.sequence(Nil: List[Rng.Rand[Int]])(SimpleRNG(100))
    val exp = (Nil: List[Int],SimpleRNG(100))
    assert(res === exp)
  }

  test("3: intsSeq") {
    assert(Rng.intsSeq(1000)(SimpleRNG(100)) === Rng.intsSeq(1000)(SimpleRNG(100)))
  }

  test("4: intsSeq == ints") {
    assert(ints(1000)(SimpleRNG(100)) === Rng.intsSeq(1000)(SimpleRNG(100)))
  }
}


package fpinscala.chap6

import org.scalatest.FunSuite
import Exercise66Test._

class Exercise66Test extends FunSuite {

  test("1: Rng.intDouble (map2)") {
    assert(genIntDouble(100,SimpleRNG(100)) === genIntDouble(100,SimpleRNG(100)))
  }

  test("2: Rng.doubleInt (map2)") {
    assert(genDoubleInt(10000,SimpleRNG(67)) === genDoubleInt(10000,SimpleRNG(67)))
  }
}

object Exercise66Test {
  def genIntDouble(n: Int,rng: RNG): List[((Int,Double),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Int,Double),RNG)]): List[((Int,Double),RNG)] = 
      if (n > 0) {
        val ((i,d),rg) = Rng.intDouble(rng)
        _gen(n-1,rg,((i,d),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  def genDoubleInt(n: Int,rng: RNG): List[((Double,Int),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Double,Int),RNG)]): List[((Double,Int),RNG)] = 
      if (n > 0) {
        val ((d,i),rg) = Rng.doubleInt(rng)
        _gen(n-1,rg,((d,i),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }
}

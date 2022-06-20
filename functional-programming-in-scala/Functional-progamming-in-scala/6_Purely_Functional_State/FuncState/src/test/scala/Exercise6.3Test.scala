package fpinscala.chap6

import org.scalatest.FunSuite
import Exercise63.{double3,doubleInt,intDouble}

class Exercise63Test extends FunSuite {

  def genDouble3(n: Int,rng: RNG): List[((Double,Double,Double),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Double,Double,Double),RNG)]): List[((Double,Double,Double),RNG)] = 
      if (n > 0) {
        val ((d1,d2,d3),rg) = double3(rng)
        _gen(n-1,rg,((d1,d2,d3),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  def genIntDouble(n: Int,rng: RNG): List[((Int,Double),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Int,Double),RNG)]): List[((Int,Double),RNG)] = 
      if (n > 0) {
        val ((i,d),rg) = intDouble(rng)
        _gen(n-1,rg,((i,d),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  def genDoubleInt(n: Int,rng: RNG): List[((Double,Int),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Double,Int),RNG)]): List[((Double,Int),RNG)] = 
      if (n > 0) {
        val ((d,i),rg) = doubleInt(rng)
        _gen(n-1,rg,((d,i),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }
  test("1: double3") {
    assert(genDouble3(100,SimpleRNG(100)) === genDouble3(100,SimpleRNG(100)))
  }

  test("2: intDouble") {
    assert(genIntDouble(100,SimpleRNG(100)) === genIntDouble(100,SimpleRNG(100)))
  }

  test("3: doubleInt") {
    assert(genDoubleInt(10000,SimpleRNG(67)) === genDoubleInt(10000,SimpleRNG(67)))
  }
}

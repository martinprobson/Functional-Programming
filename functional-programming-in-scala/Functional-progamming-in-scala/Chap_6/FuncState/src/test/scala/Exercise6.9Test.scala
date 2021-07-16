package fpinscala.chap6

import org.scalatest.FunSuite
import Exercise61.nonNegativeInt
import Exercise62.double
import Rng._

class Exercise69Test extends FunSuite {

  test("1: map test") {
    val (l,rng) = Rng.sequence( List.fill(10000)( Rng.map(nonNegativeInt)( i => i * -1) ))(SimpleRNG(1))
    assert( l.filter( _ < 0).length == 10000 && rng.isInstanceOf[RNG])
  }

  test("2: map_FM test") {
    val (l,rng) = Rng.sequence( List.fill(10000)( Rng.map_FM(nonNegativeInt)( i => i * -1) ))(SimpleRNG(1))
    assert( l.filter( _ < 0).length == 10000 && rng.isInstanceOf[RNG])
  }

  test("3: map == map_FM test") {
    val (l,rng) = Rng.sequence( List.fill(10000)( Rng.map(nonNegativeInt)( i => i * -1) ))(SimpleRNG(1))
    val (l2,rng2) =Rng.sequence( List.fill(10000)( Rng.map_FM(nonNegativeInt)( i => i * -1) ))(SimpleRNG(1))
    assert( (l,rng) === (l2,rng2))
  }

  def genIntDouble(n: Int,rng: RNG): List[((Int,Double),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Int,Double),RNG)]): List[((Int,Double),RNG)] = 
      if (n > 0) {
        val ((i,d),rg) = Rng.intDouble_FM(rng)
        _gen(n-1,rg,((i,d),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  def genDoubleInt(n: Int,rng: RNG): List[((Double,Int),RNG)] = {
    @scala.annotation.tailrec
    def _gen(n: Int, rng: RNG, l: List[((Double,Int),RNG)]): List[((Double,Int),RNG)] = 
      if (n > 0) {
        val ((d,i),rg) = Rng.doubleInt_FM(rng)
        _gen(n-1,rg,((d,i),rg) :: l) }
      else l

    _gen(n,rng,Nil) 
  }

  test("1: Rng.intDouble (map2_FM)") {
    assert(genIntDouble(100,SimpleRNG(100)) === genIntDouble(100,SimpleRNG(100)))
  }

  test("2: Rng.doubleInt (map2_FM)") {
    assert(genDoubleInt(10000,SimpleRNG(67)) === genDoubleInt(10000,SimpleRNG(67)))
  }

  test("3: Rng.intDouble_FM (map2_FM) == Rng.intDouble (map2)") {
    import Exercise66Test.{genIntDouble => genIntDoubleMap2}
    assert(genIntDouble(300,SimpleRNG(100)) === genIntDoubleMap2(300,SimpleRNG(100)))
  }

  test("4: Rng.intDouble_FM (map2_FM) == Rng.intDouble (map2)") {
    import Exercise66Test.{genDoubleInt => genDoubleIntMap2}
    assert(genDoubleInt(10000,SimpleRNG(67)) === genDoubleIntMap2(10000,SimpleRNG(67)))
  }
}

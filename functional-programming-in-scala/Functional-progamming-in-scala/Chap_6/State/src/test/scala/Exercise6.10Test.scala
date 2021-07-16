
import org.scalatest.FunSuite
import fpinscala.chap6.Exercise64.{ints => ex64Ints}
import fpinscala.chap6.{RNG,SimpleRNG}
import fpinscala.chap6.Rng.{sequence => rngSequence, 
                            map => rngMap, 
                            map2 => rngMap2,
                            int => rngInt,
                            unit => rngUnit,
                            intsSeq }

class Exercise610Test extends FunSuite {

  test("1: Exercise64.ints == Exercise6.10.ints") {
    assert(ex64Ints(1000)(SimpleRNG(100)) === Rng.ints(1000)(SimpleRNG(100)))
  }

  test("2: Exercise6.7.intsSeq == Exercise6.10.ints") {
    assert(intsSeq(1000)(SimpleRNG(100)) === Rng.ints(1000)(SimpleRNG(100)))
  }

  test("3: Check Exercise6.10.unit works the same way as Rng.unit") {
    assert(rngUnit(1)(SimpleRNG(1)) === State.unit(1).run(SimpleRNG(1)))
  }

  test("4: State map works the same way as Rng map") {
    val old = rngSequence( List.fill(100)( rngMap(rngInt)( i => i * -1) ))(SimpleRNG(1))
    val state = State.sequence( List.fill(100)(Rng.int.map(_ * -1)))(SimpleRNG(1))
    assert( old === state)
  }

  test("5: State map2 works the same way as Rng map2") {
    val old = rngMap2(rngInt,rngInt)( (a,b) => (a,b))(SimpleRNG(1))
    val state = Rng.int.map2(Rng.int)( (a,b) => (a,b)).run(SimpleRNG(1))
    assert( old === state)
  }

  test("6: State map works the same way as State map_FM") {
    val stateMap    = State.sequence( List.fill(100)(Rng.int.map(_ * -1)))(SimpleRNG(1))
    val stateMap_FM = State.sequence( List.fill(100)(Rng.int.map_FM(_ * -1)))(SimpleRNG(1))
    assert( stateMap_FM === stateMap)
  }
}


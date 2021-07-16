

package fpinscala.chap6

import org.scalatest.FunSuite
import Exercise64.ints

class Exercise64Test extends FunSuite {

  test("1: ints") {
    assert(ints(1000)(SimpleRNG(100)) === ints(1000)(SimpleRNG(100)))
  }

}


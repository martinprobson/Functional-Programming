

package fpinscala.chap4.test
import fpinscala.chap4._
import org.scalatest.FunSuite

class OptionTest extends FunSuite {

  test("map: map on Some returns a Some") {
    assert(Some(34).map(_*2) === Some(68))
  }

  test("map: map on None returns a None") {
    assert(None.map( (i: Int) => i * 2) === None)
  }


  test("mapO: mapO on Some returns a Some") {
    assert(Option.mapO(Some(34)){_*2} === Some(68))
  }

  test("mapO: mapO on None returns a None") {
    assert(Option.mapO(None){ (i: Int) => i * 2} === None)
  }

  // for flatMap testing
  def flatMapFn(x: String): Option[Double] = {
    try {
      Some(x.toDouble)
    } catch { case e: NumberFormatException => None }
  }

  test("flatMap: f does not fail on Some and returns a Some") {
    assert(Some("200").flatMap(flatMapFn) === Some(200.0))
  }

  test("flatMap: f fails on Some and returns a None") {
    assert(Some("Invalid").flatMap(flatMapFn) === None)
  }

  test("flatMap: flatMap on None returns a None") {
    assert(None.flatMap(flatMapFn) === None)
  }

  test("get: get on a None") {
    assertThrows[NoSuchElementException] {
      None.get
    }
  }

  test("get: get on a Some") {
    assert(Some(1).get === 1)
  }

  test("getOrElse: getOrElse on a Some") {
    assert(Some(1).getOrElse(2) === 1)
  }

  test("getOrElse: getOrElse on a None") {
    assert(None.getOrElse(2) === 2)
  }

  test("orElse: orElse on a None") {
    assert(None.orElse(Some(1)) === Some(1))
  }

  test("orElse: orElse on a Some") {
    assert(Some(2).orElse(Some(1)) === Some(2))
  }

  test("filter: Some -  1") {
    assert(Some(2).filter(_ != 2) === None)
  }

  test("filter: Some - 2") {
    assert(Some(2).filter(_ == 2) === Some(2))
  }

  test("filter: None") {
    assert(None.filter(_ == 2) === None)
  }
}


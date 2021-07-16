

package fpinscala.chap4.test
import fpinscala.chap4._
import org.scalatest.FunSuite

class EitherTest extends FunSuite {

  test("map: map on Left(e) returns Left(e)") {
    assert(Left("Error").map( (i: Int) => i * 2 ) === Left("Error"))
  }

  test("map: map on Right(x) returns Right(f(x))") {
    assert(Right(2).map( (i: Int) => i * 2) === Right(4))
  }

  // for flatMap testing
  def flatMapFn(x: String): Either[Exception,Double] = {
    try {
      Right(x.toDouble)
    } catch { case e: NumberFormatException => Left(e) }
  }

  test("flatMap: f does not fail on Right and returns a Right") {
    assert(Right("200").flatMap(flatMapFn) === Right(200.0))
  }

  test("flatMap: f does fail on Right and returns a Left ") {
    val expect = flatMapFn("BADNUM")
    val actual = Right("BADNUM").flatMap(flatMapFn)
    assert(actual match { case Left(e: NumberFormatException) => true
                          case _ => false })
  }

  test("flatMap: flatMap on a Left returns a Left") {
    assert(Left("ERROR").flatMap(flatMapFn) === Left("ERROR"))
  }

  test("orElse: orElse on a Right") {
    assert(Right(1).orElse(Right(2)) === Right(1))
  }

  test("orElse: orElse on a Left") {
    assert(Left(1).orElse(Right(2)) === Right(2))
  }

  def Try(a: => String): Either[Exception,Double] =
    try {
      Right(a.toDouble)
  } catch { case e: NumberFormatException => Left(e) }

  def add(x: Double, y: Double) = x + y

  def parseInput(x: String, y: String): Either[Exception,Double] = 
    Try(x).map2(Try(y))(add)
  
  test("map2 Either - test 1") {
    assert(parseInput("1.0","2.0") === Right(3.0))
  }

  test("map2 Either - test 2") {
    assert(parseInput("1x","2.0") match { case Left(e: NumberFormatException) => true
                                          case _ => false })
  }

  test("map2 Either - test 3") {
    assert(parseInput("1.0","2x") match { case Left(e: NumberFormatException) => true
                                          case _ => false })
  }

  test("map2 Either - test 4") {
    assert(parseInput("1x","2x") match { case Left(e: NumberFormatException) => true
                                          case _ => false })
  }

}





package fpinscala.chap4.test
import fpinscala.chap4._
import org.scalatest.FunSuite

class Exercise42Test extends FunSuite {

  test("mean: Some") {
    assert(Exercise42.mean(Seq(1,2,3,4,5,6,7,8,9,10)) === Some(5.5))
  }

  test("mean: None") {
    assert(Exercise42.mean(Seq()) === None)
  }

  test("variance: non-empty list") {
    assert(Exercise42.variance(Seq(1,2,3,4,5,6,7,8,9,10)) === Some(8.25))
  }

  test("variance: Empty List") {
    assert(Exercise42.variance(Seq()) === None)
  }

}



package fpinscala.chap4.test
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{None => _,Some => _,Option => _, Either => _, List => _,Nil => _,_}
import fpinscala.chap4._
import org.scalatest.FunSuite
import org.slf4j.{LoggerFactory,Logger}
import grizzled.slf4j.Logging

class OptionMap2Test extends FunSuite with Logging {

  def Try_debug[A](a: => A): Option[A] = {
    trace("In Try ")
    val ret = Option.Try(a)
    trace(s"Try is returning  $ret")
    ret
  }

  def add(x: Int, y: Int) = x + y

  def add_debug(x: Int, y: Int) = { 
    trace(s"In add x = $x y = $y")
    val ret = add(x,y)
    trace(s"add is returning $ret")
    ret
  }

  def parseInput_debug(x: String, y: String): Option[Int] = {
    trace(s"In parseInput x = $x y = $y")
    val res = Map2.map2(Try_debug(x.toInt),Try_debug(y.toInt)){ add_debug }
    trace(s"parseInput is returning $res" )
    res
  }

  test("Option map2: map2 test 1") {
    assert(parseInput_debug("1","2") === Some(3))
  }

  test("Option map2: map2 test 2") {
    assert(parseInput_debug("1x","2") === None)
  }

  test("Option map2: map2 test 3") {
    assert(parseInput_debug("1","2x") === None)
  }

  test("Option map2: map2 test 4") {
    assert(parseInput_debug("1x","2x") === None)
  }

}

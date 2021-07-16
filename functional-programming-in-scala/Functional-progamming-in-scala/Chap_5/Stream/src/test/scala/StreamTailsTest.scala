

package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamTailsTest extends FunSuite {

  test("tails: Stream() ") {
    assert(Stream().tails.toList.map(_.toList) === List(List()))
  }

  test("tails: Stream(1,2,3)") {
    assert(Stream(1,2,3).tails.toList.map(_.toList) === List(List(1,2,3),List(2,3),List(3),List()))
  }
 }

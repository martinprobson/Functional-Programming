


package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamStartsWithTest extends FunSuite {

  case class TestCond(tc: Int,s1: Stream[Int], sub: Stream[Int], expResult: Boolean)

  val testConds = List( TestCond(1,Stream(),Stream(),true),
                        TestCond(2,Stream(1),Stream(),true),
                        TestCond(3,Stream(1,2,3),Stream(),true),
                        TestCond(4,Stream(),Stream(1),false),
                        TestCond(5,Stream(),Stream(1,2,3),false),
                        TestCond(6,Stream(1,2,3),Stream(1,2,3),true),
                        TestCond(7,Stream(1,2,3,4),Stream(1,2,3),true),
                        TestCond(8,Stream(1,3,4,5),Stream(1,2,3),false),
                        TestCond(9,Stream(1,3,4,5),Stream(1),true),
                        TestCond(10,Stream(1,3,4,5),Stream(3),false),
                        TestCond(11,Stream(1,3,4,5),Stream(1,2,3,4,5),false))

  testConds.foreach{ case TestCond(n,s,sub,exp) => {
                       test(s"Stream: StartsWith - TC $n") {
                         assert(s.startsWith(sub) === exp)
                       }
                     }
                   }

  testConds.foreach{ case TestCond(n,s,sub,exp) => {
                       test(s"Stream: StartsWith_SO - TC $n") {
                         assert(s.startsWith_SO(sub) === exp)
                       }
                     }
                   }

  testConds.foreach{ case TestCond(n,s,sub,exp) => {
                       test(s"Stream: StartsWith_SO == StartsWith - TC $n") {
                         assert(s.startsWith_SO(sub) === s.startsWith(sub))
                       }
                     }
                   }

  test(s"Make sure startsWith_SO works with an infinite stream") {
    assert(Stream.from(0).startsWith_SO(Stream(0,1,2,3,4)) === true)
    assert(Stream.from(0).startsWith_SO(Stream(1,2,3,4,5)) === false)
  }

  test(s"Make sure startsWith works with an infinite stream") {
    assert(Stream.from(0).startsWith(Stream(0,1,2,3,4)) === true)
    assert(Stream.from(0).startsWith(Stream(1,2,3,4,5)) === false)
  }
}


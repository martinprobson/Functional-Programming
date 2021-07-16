

package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamForAllTest extends FunSuite {

  case class TestCond(s1: Stream[Int], p: Int => Boolean, expectedResult: Boolean) 
  val testConds = List( TestCond(Stream(1,2,3,4,5,6),(x: Int) => x > 0,true),
                       TestCond(Stream(1,2,3,0,5,6),(x: Int) => x > 0,false),
                       TestCond(Stream(1,2,3,0,5,6),(x: Int) => x > 0,false),
                       TestCond(Stream(0,2,3,4,5,6),(x: Int) => x > 0,false),
                       TestCond(Stream(1,2,3,0,5,0),(x: Int) => x > 0,false),
                       TestCond(Stream(),(x: Int) => x > 0,true))
  testConds.foreach{ case TestCond(s,p,exp) => {
                       test(s"Stream: $s.forAll_FR($p).toList == $exp") {
                         assert(s.forAll(p) === exp)
                       }
                     }
                   }
}




package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamTakeTest extends FunSuite {

  case class TestCond(s1: Stream[Int], n: Int, expectedResult: List[Int]) 
  val testConds = List( TestCond(Stream(1,2,3,4,5,6),7,List(1,2,3,4,5,6)),
                       TestCond(Stream(1,2,3,4,5,6),6,List(1,2,3,4,5,6)),
                       TestCond(Stream(1,2,3,4,5,6),5,List(1,2,3,4,5)),
                       TestCond(Stream(1,2,3,4,5,6),4,List(1,2,3,4)),
                       TestCond(Stream(1,2,3,4,5,6),3,List(1,2,3)),
                       TestCond(Stream(1,2,3,4,5,6),2,List(1,2)),
                       TestCond(Stream(1,2,3,4,5,6),1,List(1)),
                       TestCond(Stream(1,2,3,4,5,6),0,List()),
                       TestCond(Stream(),7,List()),
                       TestCond(Stream(),0,List()))
  testConds.foreach{ case TestCond(s,n,exp) => {
                       test(s"Stream: $s.take($n).toList == $exp") {
                         assert(s.take(n).toList === exp)
                       }
                     }
                   }

  testConds.foreach{ case TestCond(s,n,exp) => {
                       test(s"Stream: $s.take_TR($n).toList == $exp") {
                         assert(s.take_TR(n).toList === exp)
                       }
                     }
                   }

  testConds.foreach{ case TestCond(s,n,exp) => {
                       test(s"Stream: $s.take_NTR($n).toList == $exp") {
                         assert(s.take_NTR(n).toList === exp)
                       }
                     }
                   }

  // Test take_UF
  testConds.foreach{ case TestCond(s,n,exp) => {
                       test(s"Stream: $s.take_UF($n).toList == $exp") {
                         assert(s.take_UF(n).toList === exp)
                       }
                     }
                   }
}


package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamDropTest extends FunSuite {

  case class TestCond(s1: Stream[Int], n: Int, expectedResult: List[Int]) 
  val testConds = List( TestCond(Stream(1,2,3,4,5,6),0,List(1,2,3,4,5,6)),
                       TestCond(Stream(1,2,3,4,5,6),1,List(2,3,4,5,6)),
                       TestCond(Stream(1,2,3,4,5,6),2,List(3,4,5,6)),
                       TestCond(Stream(1,2,3,4,5,6),3,List(4,5,6)),
                       TestCond(Stream(1,2,3,4,5,6),4,List(5,6)),
                       TestCond(Stream(1,2,3,4,5,6),5,List(6)),
                       TestCond(Stream(1,2,3,4,5,6),6,List()),
                       TestCond(Stream(1,2,3,4,5,6),7,List()),
                       TestCond(Stream(),7,List()),
                       TestCond(Stream(),0,List()))
  testConds.foreach{ case TestCond(s,n,exp) => {
                       test(s"Stream: $s.drop($n).toList == $exp") {
                         assert(s.drop(n).toList === exp)
                       }
                     }
                   }
}


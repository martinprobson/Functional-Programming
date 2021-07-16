
package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamFilterTest extends FunSuite {

  case class TestCond(testNo: Int,s1: Stream[String], p: (String) => Boolean, expectedResult: Stream[String]) 
  val testConds = List( TestCond(1,Stream("a","b","c","d","e","f"),(a:String) => a == "a",Stream("a")),
                        TestCond(2,Stream("aa","bb","a1","a2","a3","ff"),(a:String) => a.startsWith("a"),Stream("aa","a1","a2","a3")),
                        TestCond(3,Stream("ab","ac","ad","ae","af","ag"),(a:String) => a.startsWith("a"),Stream("ab","ac","ad","ae","af","ag")),
                        TestCond(4,Stream("a","a","b","a","a","a"),(a:String) => a.startsWith("z"),Stream()),
                        TestCond(5,Stream(),(a:String) => a.startsWith("b"),Stream()))
  
  // Test filter
  testConds.foreach{ case TestCond(tc,s,f,exp) => {
                       test(s"filter: Test Condition $tc") {
                         assert(s.filter(f).toList === exp.toList)
                       }
                     }
                   }

}

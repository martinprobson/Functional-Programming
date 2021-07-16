
package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class StreamTakeWhileTest extends FunSuite {

  case class TestCond(testNo: Int,s1: Stream[String], p: (String) => Boolean, expectedResult: List[String]) 
  val testConds = List( TestCond(1,Stream("a","b","c","d","e","f"),(a:String) => a.startsWith("a"),List("a")),
                        TestCond(2,Stream("a","b","c","d","e","f"),(a:String) => a.startsWith("b"),List()),
                        TestCond(3,Stream("ab","ac","ad","ae","af","ag"),(a:String) => a.startsWith("a"),List("ab","ac","ad","ae","af","ag")),
                        TestCond(4,Stream("a","a","b","a","a","a"),(a:String) => a.startsWith("b"),List()),
                        TestCond(5,Stream(),(a:String) => a.startsWith("b"),List()))
  
  // Test takeWhile
  testConds.foreach{ case TestCond(tc,s,f,exp) => {
                       test(s"takeWhile: Test Condition $tc") {
                         assert(s.takeWhile(f).toList === exp)
                       }
                     }
                   }

  // Test takeWhile_FR
  testConds.foreach{ case TestCond(tc,s,f,exp) => {
                       test(s"takeWhile_FR: Test Condition $tc") {
                         assert(s.takeWhile_FR(f).toList === exp)
                       }
                     }
                   }
  //
  // Test takeWhile_UF
  testConds.foreach{ case TestCond(tc,s,f,exp) => {
                       test(s"takeWhile_UF: Test Condition $tc") {
                         assert(s.takeWhile_UF(f).toList === exp)
                       }
                     }
                   }
  //
  // Compare output of TakeWhile with TakeWhile_FR
  testConds.foreach{ case TestCond(tc,s,f,exp) => {
                       test(s"takeWhile_FR == takeWhile: Test Condition $tc") {
                         assert(s.takeWhile_FR(f).toList === s.takeWhile(f).toList)
                       }
                     }
                   }
  //
  // Compare output of TakeWhile with TakeWhile_UF
  testConds.foreach{ case TestCond(tc,s,f,exp) => {
                       test(s"takeWhile_UF == takeWhile: Test Condition $tc") {
                         assert(s.takeWhile_UF(f).toList === s.takeWhile(f).toList)
                       }
                     }
                   }
}

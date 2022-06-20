
package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class ZipWithTest extends FunSuite {

  case class Test(tc: Int,s1: Stream[Int],s2: Stream[Int], expResult: List[Int])

  val testConditions = List(  Test(1,Empty:Stream[Int],Empty:Stream[Int],Nil:List[Int]),
                              Test(2,Empty:Stream[Int],Stream(1,2,3),List(1,2,3)),
                              Test(3,Stream(1,2,3),Empty,List(1,2,3)),
                              Test(4,Stream(10),Stream(1,2,3),List(11,2,3)),
                              Test(5,Stream(10),Stream(1),List(11)),
                              Test(6,Stream(1,2,3),Stream(10),List(11,2,3)),
                              Test(7,Stream(1),Stream(10),List(11)),
                              Test(8,Stream(1,2),Stream(3,4),List(4,6)),
                              Test(9,Stream(1,2),Stream(3,4,5),List(4,6,5)),
                              Test(10,Stream(3,4,5),Stream(1,2),List(4,6,5)),
                              Test(11,Stream(1,2,3),Stream(1,2,3),List(2,4,6)),
                              Test(12,Stream(1,2,3,4,5),Stream(1,2,3,4,5),List(2,4,6,8,10)))

  // Test zipWith 
  testConditions.foreach{ case Test(tc,seq,subseq,expResult) => {
                            test(s"zipWith: Test Condition $tc") {
                              assert(seq.zipWith(subseq){ (a,b) => a+b}.toList === expResult)
                            }
                          }
                        }
                              
}

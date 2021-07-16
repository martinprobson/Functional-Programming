
package fpinscala.datastructures.test

import fpinscala.datastructures._
import org.scalatest.FunSuite

class SubsequenceTest extends FunSuite {

  case class Test(seq: List[Int],subseq: List[Int],expResult: Boolean)

  val testConditions = List(
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(6,7) ,     true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(9,10),     true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(6,7,8),    true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(1,2,3),    true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(8,9,10),   true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(1),        true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(2),        true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(10),       true),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(6,1),      false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(1,10),     false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(9,11),     false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(6,6,8),    false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(1,1,1),    false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(8,9,8),    false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(11),       false),
                          Test(List(1,2,3,4,5,6,7,8,9,10),List(12),       false),
                          Test(List(1,2)                 ,List(2),        true),
                          Test(List(1)                   ,List(1),        true),
                          Test(List(1,3,1,2,1,4)         ,List(1,2),      true),
                          Test(List(1,3,1,2,1,4)         ,List(1,3),      true),
                          Test(List(1,3,1,2,1,4)         ,List(1,4),      true),
                          Test(List(1,3,1,2,1,4)         ,List(1,1),      false),
                          Test(List(1,3,1,2,1,4)         ,List(1,2,1),    true),
                          Test(List(1,3,1,2,1,4)         ,List(1,2,1,4),  true),
                          Test(List(1,3,1,2,1,4)         ,List(1,2,1,6),  false),
                        )



  List.foreach(testConditions){ case Test(seq,subseq,expResult) => {
                            test(s"hasSubsequence($seq,$subseq) == $expResult") {
                              assert(List.hasSubsequence(seq,subseq) === expResult)
                            }
                          }
                        }
                              
}

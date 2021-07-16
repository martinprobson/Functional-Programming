package fpinscala.datastructures.test

import fpinscala.datastructures._
import org.scalatest.FunSuite

class AddListTest extends FunSuite {

  case class Test(l1: List[Int],l2: List[Int], expResult: List[Int])

  val testConditions = List(  Test(Nil:List[Int],Nil:List[Int],Nil:List[Int]),
                              Test(Nil:List[Int],List(1,2,3),List(1,2,3)),
                              Test(List(1,2,3),Nil,List(1,2,3)),
                              Test(List(10),List(1,2,3),List(11,2,3)),
                              Test(List(10),List(1),List(11)),
                              Test(List(1,2,3),List(10),List(11,2,3)),
                              Test(List(1),List(10),List(11)),
                              Test(List(1,2),List(3,4),List(4,6)),
                              Test(List(1,2),List(3,4,5),List(4,6,5)),
                              Test(List(3,4,5),List(1,2),List(4,6,5)),
                              Test(List(1,2,3),List(1,2,3),List(2,4,6)),
                              Test(List(1,2,3,4,5),List(1,2,3,4,5),List(2,4,6,8,10)))


  // Test addLists
  List.foreach(testConditions){ case Test(seq,subseq,expResult) => {
                            test(s"addLists($seq,$subseq) == $expResult") {
                              assert(List.addLists(seq,subseq) === expResult)
                            }
                          }
                        }

  // Test addLists_NTR
  List.foreach(testConditions){ case Test(seq,subseq,expResult) => {
                            test(s"addLists_NTR($seq,$subseq) == $expResult") {
                              assert(List.addLists_NTR(seq,subseq) === expResult)
                            }
                          }
                        }

  // Test zipWith (with add)
  List.foreach(testConditions){ case Test(seq,subseq,expResult) => {
                            test(s"zipWith($seq,$subseq,(a,b) => a+b) == $expResult") {
                              assert(List.zipWith(seq,subseq){ (a,b) => a+b} === expResult)
                            }
                          }
                        }
                              
}

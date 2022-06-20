

package fpinscala.chap4.test
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{None => _,Some => _,Option => _, Either => _, List => _,Nil => _,_}
import fpinscala.chap4._
import org.scalatest.FunSuite

class TraverseTest extends FunSuite {

  case class Test(tcNumber: Int,input: List[String],expected: Option[List[Int]])

  val testConditions = List(
                         Test(1,List("1","2","3","4","5","6"),        Some(List(1,2,3,4,5,6))),
                         Test(2,List("1","2","3","4","5","6","xx"),   None),
                         Test(3,Nil,                                  Some(Nil)))
 
  def conv(a: String) = Option.Try(a.toInt)

  // Test traverse function
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"traverse: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Traverse.traverse(input)(conv) === expResult)
                          }
                        }
                      }
  //
  // Test traverse_FR function
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"traverse_FR: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Traverse.traverse_FR(input)(conv) === expResult)
                          }
                        }
                      }
  //
  // Test traverse_FR == traverse 
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"traverse_FR == traverse: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Traverse.traverse_FR(input)(conv) === Traverse.traverse(input)(conv))
                          }
                        }
                      }

  // Test sequenceTraverse
  case class TestSeqTra(tcNumber: Int,input: List[Option[Int]],expected: Option[List[Int]])

  val testConditions2 = List(
                         TestSeqTra(1,List(Some(1),Some(2),Some(3)),                                   Some(List(1,2,3))),
                         TestSeqTra(2,List(Some(1),Some(2),None,Some(3)),                              None),
                         TestSeqTra(3,Nil: List[Option[Int]],                                          Some(Nil)),
                         TestSeqTra(4,List(Some(1),Some(2),Some(3),Some(4),Some(5),None),              None),
                         TestSeqTra(5,List(Some(1),Some(2),Some(3),Some(4),Some(5),Some(6),Some(7)),   Some(List(1,2,3,4,5,6,7))))
 
  // Test sequenceTraverse function
  List.foreach(testConditions2)  { case TestSeqTra(tc,input,expResult) => { 
                          test(s"sequenceTraverse: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Traverse.sequenceTraverse(input) === expResult)
                          }
                        }
                      }
  //
  // Test sequence == sequenceTraverse 
  List.foreach(testConditions2)  { case TestSeqTra(tc,input,expResult) => { 
                          test(s"sequenceTraverse == sequence: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Sequence.sequence(input) === Traverse.sequenceTraverse(input))
                          }
                        }
                      }
}

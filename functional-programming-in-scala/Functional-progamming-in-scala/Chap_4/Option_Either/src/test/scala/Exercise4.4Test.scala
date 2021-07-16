
package fpinscala.chap4.test
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{None => _,Some => _,Option => _, Either => _, List => _,Nil => _,_}
import fpinscala.chap4._
import org.scalatest.FunSuite

class SequenceTest extends FunSuite {

  case class Test(tcNumber: Int,input: List[Option[Int]],expected: Option[List[Int]])

  val testConditions = List(
                         Test(1,List(Some(1),Some(2),Some(3)),                                   Some(List(1,2,3))),
                         Test(2,List(Some(1),Some(2),None,Some(3)),                              None),
                         Test(3,Nil: List[Option[Int]],                                          Some(Nil)),
                         Test(4,List(Some(1),Some(2),Some(3),Some(4),Some(5),None),              None),
                         Test(5,List(Some(1),Some(2),Some(3),Some(4),Some(5),Some(6),Some(7)),   Some(List(1,2,3,4,5,6,7))))
 
  // Test sequence function
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"sequence: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Sequence.sequence(input) === expResult)
                          }
                        }
                      }
  //
  // Test sequence_FR function
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"sequence_FR: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Sequence.sequence_FR(input) === expResult)
                          }
                        }
                      }
  //
  // Test sequence_FR function
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"sequence_FR == sequence: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(Sequence.sequence_FR(input) === Sequence.sequence(input))
                          }
                        }
                      }
}

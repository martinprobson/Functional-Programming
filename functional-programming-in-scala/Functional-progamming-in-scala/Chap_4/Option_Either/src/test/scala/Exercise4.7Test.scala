

package fpinscala.chap4.test
import fpinscala.datastructures._
// hide Option/Either/List/Nil
import scala.{Right => _,Left => _,None => _,Some => _,Option => _, Either => _, List => _,Nil => _,_}
import fpinscala.chap4._
import org.scalatest.FunSuite

class Exercise47Test extends FunSuite {

  case class Test(tcNumber: Int,input: List[String],expected: Either[String,List[Int]])

  val testConditions = List(
                         Test(1,List("1","2","3","4","5","6"),        Right(List(1,2,3,4,5,6))),
                         Test(2,List("1","2","3","4","5","6","xx"),   Left("formatError: xx")),
                         Test(3,Nil,                                  Right(Nil)))
 
  def conv(a: String): Either[String,Int] =  {
    try { 
      Right(a.toInt) 
    } catch { case e: Exception => Left(s"formatError: $a") }
  }

  // Test traverse function
  List.foreach(testConditions)  { case Test(tc,input,expResult) => { 
                          test(s"traverse: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(EitherTraverse.traverse(input)(conv) === expResult)
                          }
                        }
                      }

  // Test sequence
  case class TestSeq(tcNumber: Int,input: List[Either[String,Int]],expected: Either[String,List[Int]])

  val testConditions2 = List(
                         TestSeq(1,List(Right(1),Right(2),Right(3)),                                       Right(List(1,2,3))),
                         TestSeq(2,List(Right(1),Right(2),Left("error"),Right(3)),                         Left("error")),
                         TestSeq(3,List(Right(1),Right(2),Left("error"),Left("error2")),                   Left("error")),
                         TestSeq(4,Nil: List[Either[String,Int]],                                          Right(Nil)),
                         TestSeq(5,List(Right(1),Right(2),Right(3),Right(4),Right(5),Left("e")),           Left("e")),
                         TestSeq(6,List(Right(1),Right(2),Right(3),Right(4),Right(5),Right(6),Right(7)),   Right(List(1,2,3,4,5,6,7))))
 
  // Test sequenceTraverse function
  List.foreach(testConditions2)  { case TestSeq(tc,input,expResult) => { 
                          test(s"sequenceTraverse: Test condition: $tc Input: $input Expected: $expResult") {
                            assert(EitherTraverse.sequence(input) === expResult)
                          }
                        }
                      }
}

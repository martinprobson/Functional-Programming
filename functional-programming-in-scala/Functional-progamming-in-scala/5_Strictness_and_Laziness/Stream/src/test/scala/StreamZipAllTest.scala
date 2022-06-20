

package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}

import org.scalatest.FunSuite

class ZipAllTest extends FunSuite {

  case class Test(tc: Int,s1: Stream[Int],s2: Stream[String], expResult: List[(Option[Int],Option[String])])

  val testConditions = List(  Test(1,Empty:Stream[Int],Empty:Stream[String],Nil:List[(Option[Nothing],Option[Nothing])]),
                              Test(2,Empty:Stream[Int],Stream("a","b","c"),List((None,Some("a")),(None,Some("b")),(None,Some("c")))),
                              Test(3,Stream(1,2,3),Empty,List((Some(1),None),(Some(2),None),(Some(3),None))),
                              Test(4,Stream(10),Stream("a","b","c"),List((Some(10),Some("a")),(None,Some("b")),(None,Some("c")))),
                              Test(5,Stream(10),Stream("a"),List((Some(10),Some("a")))),
                              Test(6,Stream(1,2,3),Stream("10"),List((Some(1),Some("10")),(Some(2),None),(Some(3),None))),
                              Test(7,Stream(1),Stream("10"),List((Some(1),Some("10")))),
                              Test(8,Stream(1,2),Stream("3","4"),List((Some(1),Some("3")),(Some(2),Some("4")))),
                              Test(9,Stream(1,2),Stream("a","b","c"),List((Some(1),Some("a")),(Some(2),Some("b")),(None,Some("c")))),
                              Test(10,Stream(1,2,3),Stream("a","b","c"),List((Some(1),Some("a")),(Some(2),Some("b")),(Some(3),Some("c")))))

  // Test zipWith 
  testConditions.foreach{ case Test(tc,seq,subseq,expResult) => {
                            test(s"zipAll: Test Condition $tc") {
                              assert(seq.zipAll(subseq).toList === expResult)
                            }
                          }
                        }
                              
}

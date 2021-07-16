
package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grizzled.slf4j.Logging
import scala.annotation.tailrec

import org.scalatest.FunSuite

class StreamTest extends FunSuite {

  test("Stream: Stream() == Empty") {
    assert(Stream() === Empty)
  }

  //TODO Need to define an equals method for streams.
  test("Stream: test apply constructs Stream elements correctly") {
    assert(Stream(1,2,3).toList  === List(1,2,3))
  }

  test("Stream: toList - empty") {
    assert(Stream().toList === List())
  }

  test("Stream: toList - non-empty") {
    assert(Stream(1,2,3).toList === List(1,2,3))
    assert(Stream(1).toList === List(1))
   }

  test("Stream: toList") {
    assert(Stream(1,2,3).toList === List(1,2,3))
    assert(Stream(1).toList === List(1))
   }
  
  test("Stream: toList_NTR - empty") {
    assert(Stream().toList_NTR === List())
  }

  test("Stream: toList_NTR - non-empty") {
    assert(Stream(1,2,3).toList_NTR === List(1,2,3))
    assert(Stream(1).toList_NTR === List(1))
   }

  test("Stream: toList_NTR") {
    assert(Stream(1,2,3).toList_NTR === List(1,2,3))
    assert(Stream(1).toList_NTR === List(1))
   }
   
  test("Stream: toList_FL - empty") {
    assert(Stream().toList_FL === List())
  }

  test("Stream: toList_FL - non-empty") {
    assert(Stream(1,2,3).toList_FL === List(1,2,3))
    assert(Stream(1).toList_FL === List(1))
   }

  test("Stream: toList_FL") {
    assert(Stream(1,2,3).toList_FL === List(1,2,3))
    assert(Stream(1).toList_FL === List(1))
   }

  test("foldRight: test") {
    assert(Stream(10.0,5.0,100.0).foldRight(1000.0)(_/_) === 0.2)
  }

  test("headOption: test on empty stream") {
    assert(Stream().headOption === None)
  }

  test("headOption: test on a non-empty stream") {
    assert(Stream(1,2,3,4,5).headOption === Some(1))
  }

  test("map: test ") {
    val s = Stream(1,2,3,4,5,6,7,8,9,10)
    val exp = Stream(2,3,4,5,6,7,8,9,10,11)
    val res = s.map{ _ + 1 }
    assert(res.toList === exp.toList)
  }

  test("map: test - Empty Stream") {
    val exp = Stream(2,3,4,5,6,7,8,9,10,11)
    val res = Stream().map{ (a:Int) => a + 1 }
    assert(res === Empty)
  }


  test("map_UF: test ") {
    val s = Stream(1,2,3,4,5,6,7,8,9,10)
    val exp = Stream(2,3,4,5,6,7,8,9,10,11)
    val res = s.map_UF{ _ + 1 }
    assert(res.toList === exp.toList)
  }

  test("map_UF: test - Empty Stream") {
    val exp = Stream(2,3,4,5,6,7,8,9,10,11)
    val res = Stream().map_UF{ (a:Int) => a + 1 }
    assert(res === Empty)
  }

  test("append: ") {
    assert(Stream(1,2,3).append(Stream(4,5,6)).toList === Stream(1,2,3,4,5,6).toList)
  }

  test("append: empty Stream ") {
    assert(Stream(1,2,3).append(Stream()).toList === Stream(1,2,3).toList)
  }

  test("append: append to an empty Stream") {
    assert(Stream().append(Stream(1,2,3)).toList === Stream(1,2,3).toList)
  }

  test("append: append two empty Streams") {
    assert(Stream().append(Stream()).toList === Stream().toList)
  }

  test("flatMap: non-empty Stream") {
    assert(Stream(1,2,3).flatMap{ x => Stream(x,x * 100) }.toList === Stream(1,100,2,200,3,300).toList)
  }

  test("flatMap: empty List") {
    assert(Stream.empty[Int].flatMap{ x => Stream(x,x * 100) }.toList === Stream().toList)
  }

  test("constant: generate infinite stream of constant value") {
    assert(Stream.constant(10).take(500).toList === List.fill(500)(10))
  }

  test("constant_UF: generate infinite stream of constant value") {
    assert(Stream.constant_UF(10).take(500).toList === List.fill(500)(10))
  }

  test("constant_UF == constant") {
    assert(Stream.constant_UF(10).take(500).toList === Stream.constant(10).take(500).toList)
  }

  test("from: generate an infinite sequence") {
    assert(Stream.from(0).take(500).toList === List.range(0,500))
  }

  test("from_UF: generate an infinite sequence unfold version") {
    assert(Stream.from_UF(0).take(500).toList === List.range(0,500))
  }

  test("from_UF == from") {
    assert(Stream.from_UF(0).take(500).toList === Stream.from(0).take(500).toList)
  }

  test("fibs: generate an infinite sequence of Fib") {
    assert(Stream.fibs.take(10).toList === List[Double](0,1,1,2,3,5,8,13,21,34))
  }

  test("fibs_UF: unfold version of fibs") {
    assert(Stream.fibs_UF.take(10).toList === List[Double](0,1,1,2,3,5,8,13,21,34))
  }

  test("fibs_UF == fibs") {
    assert(Stream.fibs_UF.take(100).toList === Stream.fibs.take(100).toList)
  }

}


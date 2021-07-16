package fpinscala.datastructures.test

import java.io.StringWriter
import scala.annotation.tailrec
import fpinscala.datastructures._
import org.scalatest.FunSuite

class ListTest extends FunSuite {

  test("List: test Nil List - Nil === List()") {
    assert(Nil  === List())
  }

  test("List: test apply constructs List elements correctly") {
    val l = List(1,2,3)
    assert(l  === Cons(1,Cons(2,Cons(3,Nil))) )
  }

  test("sum: List.sum adds up an Integer list correctly") {
    assert(List.sum(List(1,2,3)) === 6)
  }

  test("product: List.product multiplies an Integer list correctly") {
    assert(List.product(List(100,1000,300)) === 30000000)
    assert(List.product(List(0,1000,300)) === 0)
  }

  test("product: product of a list with a zero member is zero") {
    assert(List.product(List(0.0,1000.0,300.0)) === 0.0)
  }

  test("tail: tail of a list returns everything but the first element") {
    assert(List.tail(List(1,2,3)) === List(2,3))
    assert(List.tail(List(2,3)) === List(3))
    assert(List.tail(List(3)) === Nil)
  }

  test("tail: tail of an empty list throws UnsupportedOperationException ") {
    assertThrows[UnsupportedOperationException] {
      List.tail(Nil)
    }
    assertThrows[UnsupportedOperationException] {
      List.tail(List.tail(List(1)))
    }
  }

  test("setHead: setHead(h) on a Nil list returns List(h)") {
    assert(List.setHead("A",Nil) === List("A"))
  }

  test("setHead: setHead(h) on a non-empty list l should return Cons(h,List.tail(l))") {
    assert(List.setHead("A",List("B","C")) === List("A","C"))
  }

  test("drop: drop cannot accept a -ve drop argument") {
    assertThrows[IllegalArgumentException] {
      List.drop(List(1),-1)
    }
  }

  test("drop: drop 0 elements from head of list") {
    assert(List.drop(List(1,2,3,4,5),0) === List(1,2,3,4,5))
  }
  
  test("drop: drop 1 element from head of list") {
    assert(List.drop(List(1,2,3,4,5),1) === List(2,3,4,5))
  }

  test("drop: drop 2 elements from head of list") {
    assert(List.drop(List(1,2,3,4,5),2) === List(3,4,5))
  }

  test("drop: drop 3 elements from head of list") {
    assert(List.drop(List(1,2,3,4,5),3) === List(4,5))
  }

  test("drop: drop 4 elements from head of list") {
    assert(List.drop(List(1,2,3,4,5),4) === List(5))
  }

  test("drop: drop 5 elements from head of list") {
    assert(List.drop(List(1,2,3,4,5),5) === Nil)
  }

  test("drop: cannot drop from an empty list") {
    assertThrows[UnsupportedOperationException] {
      List.drop(Nil,1)
    }
  }

  test("drop: n cannot be greater than length of list") {
    assertThrows[UnsupportedOperationException] {
      List.drop(List(1,2),3)
    }
  }

  test("dropWhile: remove all -ve integers") {
    val l = List(-1,-1,-1,-10,-11,1,-1,-3,30,20,0)
      assert(List.dropWhile(l, (i: Int) => i <  0) === List(1,-1,-3,30,20,0))
  }

  test("init: init on an empty List returns Nil") {
    assert(List.init(List()) === Nil)
  }

  test("init: Returns everything bu the  last entry of a list") {
    assert(List.init(List("a","b","c","end")) === List("a","b","c"))
  }

  test("reverse: Reverse a list") {
    val l = List.buildList(1000){ _.toString } 
    assert(List.reverse(List.reverse(l)) === l)
  }

  test("concat: List of strings") {
    assert(List.concat(List("a","b","c","d")) === "abcd")
  }

  test("foldRight_NTR: test") {
    assert(List.foldRight_NTR(List(10.0,5.0,100.0),1000.0)(_/_) === 0.2)
  }

  test("foldRight: test") {
    assert(List.foldRight(List(10.0,5.0,100.0),1000.0)(_/_) === 0.2)
  }

  test("foldRight: foldRight == foldRight_NTR") {
    val l = List(0.01,0.1,1.0,10.0,100.0,1000.0)
    assert(List.foldRight(l,1000.0){_/_} === List.foldRight_NTR(l,1000.0){_/_})
  }

  test("foldLeft_NTR: test") {
    assert(List.foldLeft_NTR(List(100.0,5.0,100.0),1000.0)(_/_) === 0.02)
  }

  test("foldLeft: test") {
    assert(List.foldLeft(List(100.0,5.0,100.0),1000.0)(_/_) === 0.02)
  }

  test("foldLeft: foldLeft== foldLeft_NTR") {
    val l = List(0.01,0.1,1.0,10.0,100.0,1000.0)
    assert(List.foldLeft(l,1000.0){_/_} === List.foldLeft_NTR(l,1000.0){_/_})
  }

  test("length: test") {
    assert(List.length(List.buildList(10000){ _.toString }) === 10000)
    assert(List.length(Nil) === 0)
  }

  test("append: test") {
    assert(List.append(List(1,2,3),List(4,5,6)) === List(1,2,3,4,5,6))
  }

  test("append: empty list test") {
    assert(List.append(List(1,2,3),List()) === List(1,2,3))
  }

  test("append: append to an empty list ") {
    assert(List.append(List(),List(1,2,3)) === List(1,2,3))
  }

  test("append: append two empty lists") {
    assert(List.append(List(),List()) === List())
  }

  test("flatten: test 1") {
    assert(List.flattenList(List(List(1,2,3),List(4,5,6))) === List(1,2,3,4,5,6))
  }

  test("flatten: test 2") {
    val innerList = List.buildList(1000){ _.toString }
    val lol = List( innerList,innerList,innerList,innerList,innerList)
    val expected = List.append(innerList,
                               List.append(innerList,
                               List.append(innerList,
                               List.append(innerList,innerList))))
    val actual = List.flattenList(lol)
    assert(actual === expected)
    assert(List.length(actual) === List.length(expected))
  }

  test("flatten: test 3") {
    val lol = List(List(1),List(2),Nil,List(3),List(4,5,6),List(7,8,9,10))
    val expected = List(1,2,3,4,5,6,7,8,9,10)
    val actual = List.flattenList(lol)
    assert(actual === expected)
    assert(List.length(actual) === List.length(expected))
  }

  test("flatten: test 4") {
    val lol = List(List("Fred Bloggs"),List("11","High", "St"))
    val expected = List("Fred Bloggs","11","High", "St")
    val actual = List.flattenList(lol)
    assert(actual === expected)
    assert(List.length(actual) === List.length(expected))
  }

  test("foreach: test ") {
    val sw = new StringWriter()
    val l = List(1,2,3,4,5,6)
    List.foreach(l){ (x) => sw.write(x.toString) }
    val res = sw.toString
    assert(res === "123456")
  }

  test("map: test ") {
    val l = List(1,2,3,4,5,6,7,8,9,10)
    val exp = List(2,3,4,5,6,7,8,9,10,11)
    val res = List.map(l){ _ + 1 }
    assert(res === exp)
  }

  test("map2: test 1") {
    val l = List(1,2,3,4,5,6,7,8,9,10)
    val exp = List(2,3,4,5,6,7,8,9,10,11)
    val res = List.map2(l){ _ + 1 }
    assert(res === exp)
  }

  test("map2: test 2") {
    val l = List(1,2,3)
    val exp = List("1","2","3")
    val res = List.map2(l){ _.toString }
    assert(res === exp)
  }

  test("flatMap: non-empty List") {
    assert(FlatMap.flatMap(List(1,2,3)){ x => List(x,x * 100) } === List(1,100,2,200,3,300))
  }

  test("flatMap: empty List") {
    assert(FlatMap.flatMap(List(): List[Int]){ x => List(x,x * 100) } === List())
  }
}

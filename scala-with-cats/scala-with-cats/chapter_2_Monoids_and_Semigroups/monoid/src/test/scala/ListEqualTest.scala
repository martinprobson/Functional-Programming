import org.scalatest.FunSuite

class ListTest extends FunSuite {

  test("List: test Nil List - Nil === List()") {
    assert(Nil  === List())
  }

  test("List: test apply constructs List elements correctly") {
    val l = List(1,2,3)
    assert(l  === Cons(1,Cons(2,Cons(3,Nil))) )
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
}

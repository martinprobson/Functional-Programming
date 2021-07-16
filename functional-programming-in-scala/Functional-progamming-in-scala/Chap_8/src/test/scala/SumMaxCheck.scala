

package fpinscala.chapter8

import org.scalacheck.Prop.{forAll,BooleanOperators}
import org.scalacheck.{Properties,Gen}
import Main.{sum,max}

object ListSumSpecification extends Properties("ListSum") {

  property("The sum of a list is the same as the sum with list reversed") =  forAll { (l: List[Int]) => 
    sum(l) == sum(l.reverse)
  }

  property("sum of list with all values the same ") = forAll { (l: Int, n: Int) =>
    (l >= 0 && l <= 1000) ==> (List.fill(l)(n).length * n == sum(List.fill(l)(n)))
  }

}

object ListMaxSpecification extends Properties("ListMax") {

  property("The max of a list is the same as List.sorted.reverse.head") =  forAll { (l: List[Int]) => 
    (l.length > 0) ==> (max(l) == l.sorted.reverse.head)
  }

  val constIntList = Gen.listOf(Gen.const(10))
  // TODO Build a generator for this test to limit the max length of list
  property("max of list with all values the same ") = forAll(constIntList) { l =>
    (l.length > 0) ==> (l.head == max(l))
  }

}

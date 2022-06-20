import org.scalacheck.Prop._
import org.scalacheck.Prop
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

import Exercise10_3._

class Exercise10_3Test extends FunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def checkMonoidLaw(m: Monoid[Int => Int]): Prop = forAll{ (a1: Int => Int)               => isEqual(m.op(m.zero,a1),a1) &&
                                                                                    isEqual(m.op(a1,m.zero),a1)  } &&
                                                   forAll{ (a1: Int => Int, a2: Int => Int, a3: Int => Int) => 
                                                                  isEqual(m.op(m.op(a1,a2),a3),m.op(a1,m.op(a2,a3))) }
   

  // I have to define a custom isEqual method here as the functions (Int => Int) need to actually called to determine if they
  // are equal.
  def isEqual(a: Int => Int, b: Int => Int): Boolean = a(1) == b(1) 

  val props = Seq(
    TestCase("Check Monoid Law - firstEndoMonoid",checkMonoidLaw(firstendoMonoid).label("firstEndoMonoid")),
    TestCase("Check Monoid Law - lastEndoMonoid",checkMonoidLaw(lastendoMonoid).label("lastEndoMonoid")))

  props.foreach((a: TestCase) => test(a.name){ check(a.property) })
}

import org.scalacheck.Prop._
import org.scalacheck.{Prop,Arbitrary}
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

import Exercise10_2._

class Exercise10_2Test extends FunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def checkMonoidLaw[A: Arbitrary](m: Monoid[A]): Prop = forAll{ (a1: A, a2: A, a3: A) => m.op(m.op(a1,a2),a3) == m.op(a1,m.op(a2,a3)) } &&
                                                          forAll{ a1: A               => (m.op(m.zero,a1) == a1) && (m.op(a1,m.zero) == a1) }

  val props = Seq(
    TestCase("Check Monoid Law - firstOptionMonoid",checkMonoidLaw[Option[Int]](firstOptionMonoid).label("firstOptionMonoid")),
    TestCase("Check Monoid Law - lastOptionMonoid",checkMonoidLaw[Option[Int]](lastOptionMonoid).label("lastOptionMonoid")))

  props.foreach((a: TestCase) => test(a.name){ check(a.property) })
}


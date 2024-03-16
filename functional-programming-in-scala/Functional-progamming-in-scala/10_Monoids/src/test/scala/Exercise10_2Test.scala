import org.scalacheck.Prop._
import org.scalacheck.{Prop, Arbitrary}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers

import Exercise10_2._

class Exercise10_2Test extends AnyFunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def checkMonoidLaw[A: Arbitrary](m: Monoid[A]): Prop = forAll {
    (a1: A, a2: A, a3: A) => m.op(m.op(a1, a2), a3) == m.op(a1, m.op(a2, a3))
  } &&
    forAll { (a1: A) => (m.op(m.zero, a1) == a1) && (m.op(a1, m.zero) == a1) }

  val props = Seq(
    TestCase(
      "Check Monoid Law - firstOptionMonoid - Option[Int]",
      checkMonoidLaw[Option[Int]](firstOptionMonoid).label("firstOptionMonoid")
    ),
    TestCase(
      "Check Monoid Law - lastOptionMonoid - Option[Int]",
      checkMonoidLaw[Option[Int]](lastOptionMonoid).label("lastOptionMonoid")
    ),
    TestCase(
      "Check Monoid Law - firstOptionMonoid - Option[String]",
      checkMonoidLaw[Option[String]](firstOptionMonoid)
        .label("firstOptionMonoid")
    ),
    TestCase(
      "Check Monoid Law - lastOptionMonoid - Option[String]",
      checkMonoidLaw[Option[String]](lastOptionMonoid).label("lastOptionMonoid")
    )
  )

  props.foreach((a: TestCase) => test(a.name) { check(a.property) })
}

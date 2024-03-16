import org.scalacheck.Prop._
import org.scalacheck.{Prop, Arbitrary}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers

import Exercise10_1._

class Exercise10_1Test extends AnyFunSuite with Checkers {

  given PropertyCheckConfiguration(
    minSuccessful = 100,
    maxDiscardedFactor = 5.0,
    minSize = 100,
    workers = 1
  )

  case class TestCase(name: String, property: Prop)

  def checkMonoidLaw[A: Arbitrary](m: Monoid[A]): Prop = forAll {
    (a1: A, a2: A, a3: A) => m.op(m.op(a1, a2), a3) == m.op(a1, m.op(a2, a3))
  } &&
    forAll { (a1: A) =>
      (m.op(m.zero, a1) == a1) && (m.op(a1, m.zero) == a1)
    }
  val props = Seq(
    TestCase(
      "Check Monoid Law - intAddition",
      checkMonoidLaw[Int](intAddition).label("intAddition")
    ),
    TestCase(
      "Check Monoid Law - intMultiplication",
      checkMonoidLaw[Int](intMultiplication).label("intMultiplication")
    ),
    TestCase(
      "Check Monoid Law - stringMonoid",
      checkMonoidLaw[String](stringMonoid).label("stringMonoid")
    ),
    TestCase(
      "Check Monoid Law - listMonoid",
      checkMonoidLaw[List[Int]](listMonoid).label("listMonoid")
    ),
    TestCase(
      "Check Monoid Law - booleanOr",
      checkMonoidLaw[Boolean](booleanOr).label("booleanOr")
    ),
    TestCase(
      "Check Monoid Law - booleanAnd",
      checkMonoidLaw[Boolean](booleanAnd).label("booleanAnd")
    )
  )

  props.foreach((a: TestCase) => test(a.name) { check(a.property) })
}

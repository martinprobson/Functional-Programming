import org.scalacheck.Prop._
import org.scalacheck.{Prop, Arbitrary}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers

import Exercise10_1._
import Exercise10_6._

class Exercise10_6Test extends AnyFunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def foldLeftTest[A: Arbitrary](m: Monoid[A]): Prop = forAll { (a1: List[A]) =>
    foldLeft(a1, m.zero)(m.op) ==
      a1.foldLeft(m.zero)(m.op)
  }

  val props = Seq(
    TestCase(
      "foldMap implementation of foldLeft - intAddition",
      foldLeftTest[Int](intAddition).label("intAddition")
    ),
    TestCase(
      "foldMap implementation of foldLeft - intMultiplication",
      foldLeftTest[Int](intMultiplication).label("intMultiplication")
    ),
    TestCase(
      "foldMap implementation of foldLeft - stringMonoid",
      foldLeftTest[String](stringMonoid).label("stringMonoid")
    ),
    TestCase(
      "foldMap implementation of foldLeft - listMonoid",
      foldLeftTest[List[Int]](listMonoid).label("listMonoid")
    ),
    TestCase(
      "foldMap implementation of foldLeft - booleanOr",
      foldLeftTest[Boolean](booleanOr).label("booleanOr")
    ),
    TestCase(
      "foldMap implementation of foldLeft - booleanAnd",
      foldLeftTest[Boolean](booleanAnd).label("booleanAnd")
    )
  )

  props.foreach((a: TestCase) => test(a.name) { check(a.property) })

}

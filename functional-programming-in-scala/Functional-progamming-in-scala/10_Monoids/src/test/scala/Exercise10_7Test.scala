import org.scalacheck.Prop._
import org.scalacheck.{Prop,Arbitrary}
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

import Exercise10_1._
import Exercise10_7._

class Exercise10_7Test extends FunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def foldMapVTest[A: Arbitrary](m: Monoid[A]): Prop = forAll{ a1: IndexedSeq[A] => foldMapV(a1,m)( (a: A) => a) ==
                                                                            a1.foldLeft(m.zero)(m.op)}
  val props = Seq(
    TestCase("foldMapV == foldLeft - intAddition",foldMapVTest[Int](intAddition).label("intAddition")),
    TestCase("foldMapV == foldLeft - intMultiplication",foldMapVTest[Int](intMultiplication).label("intMultiplication")),
    TestCase("foldMapV == foldLeft - stringMonoid",foldMapVTest[String](stringMonoid).label("stringMonoid")),
    TestCase("foldMapV == foldLeft - listMonoid",foldMapVTest[List[Int]](listMonoid).label("listMonoid")),
    TestCase("foldMapV == foldLeft - booleanOr",foldMapVTest[Boolean](booleanOr).label("booleanOr")),
    TestCase("foldMapV == foldLeft - booleanAnd",foldMapVTest[Boolean](booleanAnd).label("booleanAnd")))

  props.foreach((a: TestCase) => test(a.name){ check(a.property) })
}

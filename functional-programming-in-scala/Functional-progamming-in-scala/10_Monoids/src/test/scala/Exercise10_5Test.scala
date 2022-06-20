import org.scalacheck.Prop._
import org.scalacheck.{Prop,Arbitrary}
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

import Exercise10_1._
import Exercise10_5._

class Exercise10_5Test extends FunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def foldMapTest[A: Arbitrary](m: Monoid[A]): Prop = forAll{ a1: List[A] => foldMap[A,A](a1,m)( (a: A) => a) ==
                                                                        a1.foldLeft(m.zero)(m.op)}

  val props = Seq(
    TestCase("foldMap check - intAddition",foldMapTest[Int](intAddition).label("intAddition")),
    TestCase("foldMap check - intMultiplication",foldMapTest[Int](intMultiplication).label("intMultiplication")),
    TestCase("foldMap check - stringMonoid",foldMapTest[String](stringMonoid).label("stringMonoid")),
    TestCase("foldMap check - listMonoid",foldMapTest[List[Int]](listMonoid).label("listMonoid")),
    TestCase("foldMap check - booleanOr",foldMapTest[Boolean](booleanOr).label("booleanOr")),
    TestCase("foldMap check - booleanAnd",foldMapTest[Boolean](booleanAnd).label("booleanAnd")))

  props.foreach((a: TestCase) => test(a.name){ check(a.property) })
}

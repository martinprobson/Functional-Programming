import org.scalacheck.Prop._
import org.scalacheck.{Prop,Arbitrary}
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalatest.fixture
import java.util.concurrent.{ExecutorService,Executors,Callable}

import Par._
import Exercise10_1._
import Exercise10_8._

class Exercise10_8Test extends fixture.FunSuite with Checkers {

  case class TestCase(name: String, property: Prop)

  def checkAssociativeLaw[A](m: Monoid[Par[A]], a1: A, a2: A, a3: A,es: ExecutorService): Boolean = {
     (m.op(m.op(lazyUnit(a1),lazyUnit(a2)),lazyUnit(a3))(es).get == m.op(lazyUnit(a1),m.op(lazyUnit(a2),lazyUnit(a3)))(es).get)
  }

  //
  // Have to pass in an ExecutorService here so that we can get to the results of the Par to compare....
  def checkIdentityLaw[A](m: Monoid[Par[A]], a1: A, es: ExecutorService): Boolean = {
    (m.op(m.zero,lazyUnit(a1))(es).get == a1) && (m.op(lazyUnit(a1),m.zero)(es).get == a1)
  }


  //
  // The Monoid[A] is converted into a Monoid[Par[A]] by the Exercise10.8 par function...
  //
  def checkParMonoidLaw[A: Arbitrary](m: Monoid[A], es: ExecutorService): Prop = forAll{ (a1: A, a2: A, a3: A) => checkAssociativeLaw[A](par(m),a1,a2,a3,es) } &&
                                                          forAll{ a1: A               => checkIdentityLaw[A](par(m),a1,es) }
  type FixtureParam = ExecutorService
  
  /**
   * All the tests should share the same ExecutorService, this can
   * be done with a OneArgTest fixture.
   * @see [[http://www.scalatest.org/user_guide/sharing_fixtures#withFixtureOneArgTest withFixtureOneArgTest]]
   * 
   */
  def withFixture(test: OneArgTest) = {
    val es = Executors.newFixedThreadPool(10)

    try {
      withFixture(test.toNoArgTest(es))
    } finally es.shutdown
  }

  test("Test par version of Monoid") {
    es => {
      val props = Seq(TestCase("Check Par Monoid Law - intAddition",checkParMonoidLaw[Int](intAddition,es).label("intAddition")),
        TestCase("Check Par Monoid Law - intMultiplication",checkParMonoidLaw[Int](intMultiplication,es).label("intMultiplication")),
        TestCase("Check Monoid Law - stringMonoid",checkParMonoidLaw[String](stringMonoid,es).label("stringMonoid")),
        TestCase("Check Monoid Law - listMonoid",checkParMonoidLaw[List[Int]](listMonoid,es).label("listMonoid")),
        TestCase("Check Monoid Law - booleanOr",checkParMonoidLaw[Boolean](booleanOr,es).label("booleanOr")),
        TestCase("Check Monoid Law - booleanAnd",checkParMonoidLaw[Boolean](booleanAnd,es).label("booleanAnd")))
      props.foreach((a: TestCase) => check(a.property))
    }
  }

}


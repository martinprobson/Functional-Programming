import org.scalacheck.Prop._
import org.scalacheck.{Arbitrary, Prop}
import org.scalatest.funsuite.FixtureAnyFunSuite
import org.scalatest.Outcome
import org.scalatestplus.scalacheck.Checkers

import java.util.concurrent.{ExecutorService, Executors}
import Par._
import Exercise10_1._
import Exercise10_8._

class Exercise10_8Test extends FixtureAnyFunSuite with Checkers with Logging {

  case class TestCase(name: String, property: Prop)

  def checkAssociativeLaw[A](
      m: Monoid[Par[A]],
      a1: A,
      a2: A,
      a3: A,
      es: ExecutorService
  ): Boolean = {
    m.op(m.op(lazyUnit(a1), lazyUnit(a2)), lazyUnit(a3))(es).get == m
      .op(lazyUnit(a1), m.op(lazyUnit(a2), lazyUnit(a3)))(es)
      .get
  }

  //
  // Have to pass in an ExecutorService here so that we can get to the results of the Par to compare....
  def checkIdentityLaw[A](
      m: Monoid[Par[A]],
      a1: A,
      es: ExecutorService
  ): Boolean = {
    (m.op(m.zero, lazyUnit(a1))(es).get == a1) && (m
      .op(lazyUnit(a1), m.zero)(es)
      .get == a1)
  }

  //
  // The Monoid[A] is converted into a Monoid[Par[A]] by the Exercise10.8 par function...
  //
  def checkParMonoidLaw[A: Arbitrary](m: Monoid[A], es: ExecutorService): Prop =
    forAll { (a1: A, a2: A, a3: A) =>
      checkAssociativeLaw[A](par(m), a1, a2, a3, es)
    } &&
      forAll { (a1: A) => checkIdentityLaw[A](par(m), a1, es) }
  type FixtureParam = ExecutorService

  /** All the tests should share the same ExecutorService, this can be done with
    * a OneArgTest fixture.
    * @see
    *   [[http://www.scalatest.org/user_guide/sharing_fixtures#withFixtureOneArgTest withFixtureOneArgTest]]
    */
  def withFixture(test: OneArgTest): Outcome =
    withFixture(test.toNoArgTest(useVirtualThreads))

  lazy val usePlatformThreads: ExecutorService =
    Executors.newThreadPerTaskExecutor(Thread.ofPlatform().name("platform-", 0).factory())

  lazy val useVirtualThreads: ExecutorService =
    Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-", 0).factory())

  test("Test parFoldMap") { es =>
    val idxseq = Range(1,1000000).inclusive.toList.toIndexedSeq
    val result = Par.run(es)(parFoldMap(idxseq, intAddition)(i => i + 1)).get
    assert(result === Range(1,1000000).inclusive.map(_+1).sum)
  }
  test("Test par version of Monoid") { es =>
    {
      val props = Seq(
        TestCase(
          "Check Par Monoid Law - intAddition",
          checkParMonoidLaw[Int](intAddition, es).label("intAddition")
        ),
        TestCase(
          "Check Par Monoid Law - intMultiplication",
          checkParMonoidLaw[Int](intMultiplication, es)
            .label("intMultiplication")
        ),
        TestCase(
          "Check Monoid Law - stringMonoid",
          checkParMonoidLaw[String](stringMonoid, es).label("stringMonoid")
        ),
        TestCase(
          "Check Monoid Law - listMonoid",
          checkParMonoidLaw[List[Int]](listMonoid, es).label("listMonoid")
        ),
        TestCase(
          "Check Monoid Law - booleanOr",
          checkParMonoidLaw[Boolean](booleanOr, es).label("booleanOr")
        ),
        TestCase(
          "Check Monoid Law - booleanAnd",
          checkParMonoidLaw[Boolean](booleanAnd, es).label("booleanAnd")
        )
      )
      props.foreach((a: TestCase) => check(a.property))
    }
  }

}

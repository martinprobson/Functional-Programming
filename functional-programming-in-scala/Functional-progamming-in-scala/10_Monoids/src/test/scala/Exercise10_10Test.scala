import Exercise10_10.WCMonoid
import org.scalacheck.Gen.oneOf
import org.scalacheck.Prop.*
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers

class Exercise10_10Test extends AnyFunSuite with Checkers {

  given PropertyCheckConfiguration(
    minSuccessful = 100,
    maxDiscardedFactor = 5.0,
    minSize = 100,
    workers = 1
  )

  val nonNegativeInteger = {
    Gen.posNum[Int] suchThat (_ > 0)
  }

  def genPart: Gen[Part] = for {
    l <- Gen.alphaStr
    w <- nonNegativeInteger
    r <- Gen.alphaStr
  } yield Part(l, w, r)

  def genStub: Gen[Stub] = Gen.alphaStr.map(s => Stub(s))

  given Arbitrary[WC] = Arbitrary(oneOf(genStub, genPart))

  def checkAssociativityLaw[A: Arbitrary](m: Monoid[A]): Prop = forAll {
    (a1: A, a2: A, a3: A) => m.op(m.op(a1, a2), a3) == m.op(a1, m.op(a2, a3))
  }

  def checkIdentityLaw[A: Arbitrary](m: Monoid[A]): Prop =
    forAll { (a1: A) =>
      (m.op(m.zero, a1) == a1) && (m.op(a1, m.zero) == a1)
    }

  test("Check WCMonoid meets the Associativity law") {
    check(checkAssociativityLaw[WC](WCMonoid))
  }

  test("Check WCMonoid meets the Identity law") {
    check(checkIdentityLaw[WC](WCMonoid))
  }
}

import Exercise10_7.*
import Exercise10_9.*
import org.scalacheck.Gen.oneOf
import org.scalacheck.Prop.*
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.Outcome
import org.scalatest.funsuite.{AnyFunSuite, FixtureAnyFunSuite}
import org.scalatestplus.scalacheck.Checkers


class Exercise10_9Test extends AnyFunSuite with Checkers with Logging {

  // Generator for integer list
  val genIntList: Gen[List[Int]] = Gen.listOfN(500, Gen.choose(1, 50000))

  // Generator for indexed sequence from list above
  val genIndexSeq: Gen[IndexedSeq[Int]] = genIntList.map(l => l.toIndexedSeq)

  // Generator for sorted indexed sequence from list above
  val genSortedIndexSeq: Gen[IndexedSeq[Int]] = genIntList.map(l => l.sorted.toIndexedSeq)

  // Run 10000 successful test cases
  given PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 10000)
  given Arbitrary[IndexedSeq[Int]] = Arbitrary(oneOf(genIndexSeq,genSortedIndexSeq))

  val checkSequenceMonoid: Prop = forAll((is: IndexedSeq[Int]) => {
    val actual = foldMapV(is, sequenceMonoid) { i => (i, None) }
    val expected = is == is.sorted
    logger.info(s"Checking $is Expected: $expected Actual: $actual")
    actual._2.get == expected
  })

  test("CheckSequenceMonoid") {
    check(checkSequenceMonoid)
  }
}

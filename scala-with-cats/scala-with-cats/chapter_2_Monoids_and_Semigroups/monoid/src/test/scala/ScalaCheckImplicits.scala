

/**
 * Implicit generators for List[Int] and List[String] that Scala check can use to generate
 * test values
 */
import cats.implicits._
import org.scalacheck._
import Gen._

object ScalaCheckImplicits   {


  val genIntList: Gen[List[Int]] = for {
    intArray <- Gen.containerOfN[Array,Int](1000,Gen.choose(-10000,10000))
    intList <- intArray.foldLeft(Nil: List[Int]){ (t,i) => Cons(i,t) }
  } yield intList 

  implicit lazy val arbListInt: Arbitrary[List[Int]] = Arbitrary(genIntList)

  val genStringList: Gen[List[String]] = for {
    stringArray <- Gen.containerOfN[Array,String](1000,Gen.alphaStr)
    stringList <- stringArray.foldLeft(Nil: List[String]){ (t,i) => Cons(i,t) }
  } yield stringList 

  implicit lazy val arbListString: Arbitrary[List[String]] = Arbitrary(genStringList)

}

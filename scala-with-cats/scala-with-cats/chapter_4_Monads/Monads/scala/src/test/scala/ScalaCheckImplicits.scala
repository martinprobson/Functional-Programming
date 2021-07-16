

/**
 * Implicit generators for List[Int] and List[String] that Scala check can use to generate
 * test values
 */
import cats.implicits._
import org.scalacheck._
import Gen._

object ScalaCheckImplicits   {


 // val genIntList: Gen[MyList[Int]] = for {
 //   intArray <- Gen.containerOfN[Array,Int](100,Gen.choose(-10000,10000))
 //   intList <- intArray.foldLeft(Nil: MyList[Int]){ (t, i) => Cons(i,t) }
 // } yield intList

  val genIntList2: Gen[MyList[Int]] =
    Gen.containerOfN[Array,Int](100,Gen.choose(-1000,10000))
    .flatMap( intArray => intArray.foldLeft(Nil: MyList[Int]){ (t,i) => Cons(i,t) })

  implicit lazy val arbListInt: Arbitrary[MyList[Int]] = Arbitrary(genIntList2)

  val genStringList: Gen[MyList[String]] = for {
    stringArray <- Gen.containerOfN[Array,String](100,Gen.alphaStr)
    stringList <- stringArray.foldLeft(Nil: MyList[String]){ (t, i) => Cons(i,t) }
  } yield stringList

  implicit lazy val arbListString: Arbitrary[MyList[String]] = Arbitrary(genStringList)

}

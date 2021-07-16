import cats.implicits._
import org.scalacheck._
import org.scalacheck.Prop.forAll
import Gen._
import Tree._

object TreeSpec extends Properties("TreeSpec") {

  property("equalTest") = forAll { t: Tree[Int] =>
    (t === t) === true
  }
  property("notEqualTest") = forAll { t: Tree[Int] =>
    (t === t.map( (i: Int) => i*2)) === false
  }

  property("mapTest") = forAll { t: Tree[Int] =>
    Tree.maximum(t.map( (i: Int) => i*2)) === Tree.maximum(t)*2
  }

  val genTree: Gen[Tree[Int]] = for {
    intList <- Gen.containerOfN[List,Int](1000,Gen.choose(-10000,10000))
    tree <- intList.foldLeft(Nil: Tree[Int]){ (t,i) => insert(i,t) }
  } yield tree
  // The same code as above, but desugered....
  val genTree2: Gen[Tree[Int]] =
    Gen.containerOfN[List,Int](1000,Gen.choose(-10000,10000)).flatMap(intList => intList.foldLeft(Nil: Tree[Int]){ (t,i) => insert(i,t)}) //.map(tree => tree))
  implicit lazy val arbTree: Arbitrary[Tree[Int]] = Arbitrary(genTree2)
}

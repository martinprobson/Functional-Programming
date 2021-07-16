import cats.implicits._
import org.scalacheck._
import org.scalacheck.Prop.forAll
import Gen._

import ScalaCheckImplicits._

object ListConcatMonoidSpec extends Properties("ListConcatMonoidSpec") {

  val m = MonoidInstances(new ListConcatMonoid[String])

  property("associativeLaw") = forAll { (x: List[String], y: List[String], z: List[String]) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: List[String]) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

object ListConcatMonoidSpec2 extends Properties("ListConcatMonoidSpec2") {

  val m = MonoidInstances(new ListConcatMonoid[Int])

  property("associativeLaw") = forAll { (x: List[Int], y: List[Int], z: List[Int]) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: List[Int]) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }

}


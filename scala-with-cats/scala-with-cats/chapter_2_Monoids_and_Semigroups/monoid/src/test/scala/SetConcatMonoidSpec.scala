
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import cats.implicits._

object SetConcatMonoidSpec extends Properties("SetConcatMonoidSpec") {

  val m = MonoidInstances(new SetConcatMonoid[String])

  property("associativeLaw") = forAll { (x: Set[String], y: Set[String], z: Set[String]) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: Set[String]) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

object SetConcatMonoidSpec2 extends Properties("SetConcatMonoidSpec2") {

  val m = MonoidInstances(new SetConcatMonoid[Char])

  property("associativeLaw") = forAll { (x: Set[Char], y: Set[Char], z: Set[Char]) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: Set[Char]) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

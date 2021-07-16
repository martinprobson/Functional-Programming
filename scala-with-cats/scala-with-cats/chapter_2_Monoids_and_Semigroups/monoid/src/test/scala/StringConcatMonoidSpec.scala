import cats.implicits._
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

object StringConcatMonoidSpec extends Properties("StringConcatMonoidSpec") {

  val m = MonoidInstances.stringConcatMonoid

  property("associativeLaw") = forAll { (x: String, y: String, z: String) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: String) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

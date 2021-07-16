
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import cats.implicits._

object BooleanAndMonoidSpec extends Properties("BooleanAndMonoidSpec") {

  val m = MonoidInstances(BooleanAndMonoid)

  property("associativeLaw") = forAll { (x: Boolean, y: Boolean, z: Boolean) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: Boolean) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

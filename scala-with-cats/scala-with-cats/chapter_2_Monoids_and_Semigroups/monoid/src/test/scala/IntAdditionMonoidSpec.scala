import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import cats.implicits._

object IntAdditionMonoidSpec extends Properties("IntAdditionMonoidSpec") {

  val m = MonoidInstances(IntAdditionMonoid)

  property("associativeLaw") = forAll { (x: Int, y: Int, z: Int) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: Int) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

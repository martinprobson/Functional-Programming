import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import cats.implicits._

object IntMultiplicationMonoidSpec extends Properties("IntMultiplicationMonoidSpec") {

  val m = MonoidInstances(IntMultiplicationMonoid)

  property("associativeLaw") = forAll { (x: Int, y: Int, z: Int) =>
      m.combine(x,m.combine(y,z)) === m.combine(m.combine(x,y),z)
  }

  property("identityLaw") = forAll { (x: Int) =>
    (m.combine(x,m.empty) === x) &&
      (m.combine(m.empty,x) === x)
  }
}

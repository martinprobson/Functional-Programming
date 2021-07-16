
import cats.implicits._
import org.scalacheck._
import org.scalacheck.Prop.forAll
import Gen._

import ScalaCheckImplicits._

object intListAddSpec extends Properties("intListAddSpec") {

  property("List[Int] addition") = forAll { (l: List[Int]) =>
    Main.add(l) === List.sum(l)
  }

}

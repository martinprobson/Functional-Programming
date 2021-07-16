
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import cats.implicits._

object ListAddSpec extends Properties("ListAddSpec") {

  property("List[Int] addition") = forAll { (l: List[Int]) =>
    Main.add(l) === l.sum
  }

  property("List[Long] addition") = forAll { (l: List[Long]) =>
    Main.add(l) === l.sum
  }

}

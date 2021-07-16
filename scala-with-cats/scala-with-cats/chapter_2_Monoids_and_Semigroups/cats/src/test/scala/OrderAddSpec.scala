
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import org.scalacheck._
import Gen._
import Arbitrary.arbitrary
import cats.implicits._

object OrderAddSpec extends Properties("OrderAddSpec") {

  property("List[Order] addition") = forAll { (l: List[Order]) =>
    Main.add(l) === sum(l)
  }

  private def sum(l: List[Order]): Order =
    l.foldLeft(Order(0,0)){
      (acc: Order, o: Order) => Order(acc.totalCost + o.totalCost,
        acc.quantity + o.quantity)}

  //
  // Following code sets up a Generator for Orders and List[Order].
  // Scala check uses the implicit arbOrders definition to generate
  // order lists for the forAll test above....
  def orderGen: Gen[Order] = for {
    x <- arbitrary[Double]
    y <- arbitrary[Double]
  } yield Order(x,y)

  val genOrderList = Gen.containerOf[List,Order](orderGen)
  implicit lazy val arbOrders: Arbitrary[List[Order]] = Arbitrary(Gen.containerOf[List,Order](orderGen))

}

import cats.Monoid
import cats._
import cats.implicits._

case class Order(totalCost: Double, quantity: Double)

object Order {
  implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
    def combine(x: Order, y: Order) =
      Order(x.totalCost + y.totalCost, x.quantity + y.quantity)

    def empty = Order(0,0)
  }

  implicit val orderEq: Eq[Order] =
    Eq.instance[Order] { (o1,o2) =>
      o1.totalCost === o2.totalCost &&
      o1.quantity  === o2.quantity
    }
}

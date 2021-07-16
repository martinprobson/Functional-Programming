package coffeev2
/**
*
* Order coffee app - Version 2 functional version
*/

case class Charge(cc: CreditCard,amt: Double)

class CreditCard {
  def charge(amt: Double) {
    println("Credit card charged:" + amt)
  }
}

case class Coffee(price: Double = 1.29)

class Cafe {
  /*
  * Buy Coffee with no side effects
  */
  def buyCoffee(cc: CreditCard): (Coffee,Charge) = {
    
    val cup = new Coffee()
    (cup,Charge(cc,cup.price))
  }
}

object Cafe extends App {
  val cafe = new Cafe
  println(cafe.buyCoffee(new CreditCard))
}

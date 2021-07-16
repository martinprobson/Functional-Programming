package coffeev3
/**
*
* Order coffee app - Version 2 functional version
*/

trait PaymentMethod {

  protected var balance = 0.00

  def charge(amt: Double) {
    balance += amt
    println("charged: " + amt)
  }

  def getBalance = balance
}


class DebitCard extends PaymentMethod {
  override def charge(amt: Double) {
    super.charge(amt)
    println("Debit card charged:" + amt)
  }
}

class CreditCard extends PaymentMethod {

  val commission = 0.05

  override def charge(amt: Double) {
    val chg = amt * (1 + commission)
    super.charge(chg)
    println("Credit card charged:" + chg)
  }
}

case class Coffee(price: Double = 1.29)

case class Charge(payMeth: PaymentMethod,amt: Double)

class Cafe {
  /*
  * Buy Coffee with no side effects
  */
  def buyCoffee(payMeth: PaymentMethod): (Coffee,Charge) = {
    
    val cup = new Coffee()
    (cup,Charge(payMeth,cup.price))
  }
}

object Cafe extends App {
  val cafe = new Cafe
  val (coffee, charge) = cafe.buyCoffee(new CreditCard)
  println(cafe.buyCoffee(new CreditCard))
}


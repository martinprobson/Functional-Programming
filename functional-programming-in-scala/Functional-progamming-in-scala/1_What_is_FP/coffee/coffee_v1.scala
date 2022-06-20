package coffeev1

/**
*
* Order coffee app - Version 1 with side effects
*/

class CreditCard {
  def charge(amt: Double) {
    println("Credit card charged:" + amt)
  }
}

case class Coffee(price: Double = 1.29)

class Cafe {
  /*
  * Buy Coffee with side effects
  *
  * The buyCoffee method takes a creditcard to charge
  * and returns a Coffee object.
  * But it mutates the CreditCard object as a side effect
  * in the process. Two majors problems with this approach: -
  *
  * 1) The buyCoffee object is difficult to test.
  * 2) It is difficult to batch-up calls to buyCoffee without
  *    multiple calls the CreditCard object.
  */
  def buyCoffee(cc: CreditCard): Coffee = {
    
    val cup = new Coffee()
    cc.charge(cup.price)
    cup
  }
}

object Cafe extends App {
  val cafe = new Cafe
  println(cafe.buyCoffee(new CreditCard))
}


package coffeev4
/**
 *
 * Order coffee app - Version 4 As implemented from book
 */
class CreditCard

case class Coffee(price: Double = 1.29)

case class Charge(cc: CreditCard, amount: Double) {

	def combine(other: Charge): Charge = {
	  if (other.cc == cc)
	    Charge(cc, amount + other.amount)
	  else
	    throw new Exception("Cannot combine two different cards")
	}
}

object Charge {
        def coalesce(charges: List[Charge]): List[Charge] =
          charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList
}


class Cafe {
	/*
	 * Buy Coffee with no side effects
	 */
	def buyCoffee(cc: CreditCard): (Coffee, Charge) = {

	  val cup = new Coffee()
	  (cup, Charge(cc, cup.price))
	}

	def buyCoffees(num: Int, cc: CreditCard): (List[Coffee],Charge) = {

	  val purchases: List[(Coffee,Charge)] = List.fill(num)(buyCoffee(cc))
	  val (coffees,charges) = purchases.unzip
		(coffees, charges.reduce( (c1,c2) => c1.combine(c2)))
	}

}

object Cafe extends App {
        import Charge._

        val cafe = new Cafe      

        var charges: List[Charge] = Nil        
        val cc = new CreditCard
        val (coffees, charge) = cafe.buyCoffees(5,cc)
        charges = charge :: charges
        val (coffees2, charge2) = cafe.buyCoffees(5,cc)
        charges = charge2 :: charges

        println(coalesce(charges))                  
}



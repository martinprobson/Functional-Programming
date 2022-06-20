
case class Customer(value: Int)
case class Consultant(portfolio: List[Customer])
case class Branch(consultants: List[Consultant])
case class Company(branches: List[Branch])

/*
* A simple example of how the for comprehension works in scala and how it is just
* syntax suger for flatMap/map.
*/

object ForComprehension extends App {
  def getCompanyValue(company: Company): Int = {

    val valuesList = for {
      branch     <- company.branches
      consultant <- branch.consultants
      customer   <- consultant.portfolio
    } yield (customer.value)

    valuesList reduce (_ + _)
  }

  def getCompanyValue2(company: Company): Int = {

    val valuesList = company.branches
                     .flatMap( branch => branch.consultants
                     .flatMap( consultant => consultant.portfolio
                     .map( customer => customer.value )))
    valuesList reduce (_ + _)
  }

  val c = Company(List(
		Branch(List(
		  Consultant(List(
		    Customer(10),
		    Customer(10),
		    Customer(100))),
		  Consultant(List(
		    Customer(20),
		    Customer(20),
		    Customer(200))))),
		Branch(List(
		  Consultant(List(
		    Customer(10),
		    Customer(10),
		    Customer(100))),
		  Consultant(List(
		    Customer(20),
		    Customer(20),
		    Customer(200))))),
		Branch(List(
		  Consultant(List(
		    Customer(10),
		    Customer(10),
		    Customer(100))),
		  Consultant(List(
		    Customer(20),
		    Customer(20),
		    Customer(200)))))))

  println(getCompanyValue(c))
  println(getCompanyValue2(c))
}

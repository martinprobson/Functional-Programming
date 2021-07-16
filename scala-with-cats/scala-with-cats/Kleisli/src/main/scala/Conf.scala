import cats.data.Kleisli
import cats.implicits._


case class Name(firstName: String, lastName: String)
case class Age(age: Int)
case class Person(name: Name, age: Age)

case class Conf(name: String, age: Int)

//
// Example 1 - Both of our configuration functions take a Config object as input.
//
object Example1 extends App {

  def readName: Conf => Option[Name] = c => c.name.split(" ") match {
    case Array() => None
    case Array(_) => None
    case Array(f, s@_*) => Some(Name(f,s.mkString(" ")))
  }

  def readAge: Conf => Option[Age] = c => if ((c.age < 1) || (c.age > 100)) None else Some(Age(c.age))

  val configure = for {
    name <- Kleisli(readName)
    age <- Kleisli(readAge)
  } yield Person(name,age)

  println(configure(Conf("Martin Robson",55)))
  println(configure(Conf("Martin Peter Robson",56)))
  println(configure(Conf("Martin",56)))
  println(configure(Conf("Martin Peter Robson",-56)))
}

//
// Example 2 - In this example our Config objects take different inputs (String and Int)
//             and we therefore use the local function to widen the input types
object Example2 extends App {

  def readName: String => Option[Name] = _.split(" ") match {
    case Array() => None: Option[Name]
    case Array(_) => None: Option[Name]
    case Array(f, s@_*) => Some(Name(f,s.mkString(" ")))
  }

  def readAge: Int => Option[Age] = c => if ((c < 1) || (c > 100)) None else Some(Age(c))

  val configure = for {
    name <- Kleisli(readName).local[Conf](_.name)
    age <- Kleisli(readAge).local[Conf](_.age)
  } yield Person(name,age)

  println(configure(Conf("Martin Robson",55)))
  println(configure(Conf("Martin Peter Robson",56)))
  println(configure(Conf("Martin",56)))
  println(configure(Conf("Martin Peter Robson",-56)))
}


import com.typesafe.config.ConfigFactory
object Example3 extends App {
  val conf = ConfigFactory.load()
  val name = conf.getConfig("DbParms").getString("user")
  println(name)
}

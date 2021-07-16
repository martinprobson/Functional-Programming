package typeclass.printable

import common.{Cat, Person}

//
// These are our type instances
//
// In Haskell this would be: -
//
// data common.Person = common.Person { name :: String
//                       , email :: String }
//
// instance Printable String where 
//    format s = "String " ++ show s 
//
// instance Printable common.Person where
//    format p = "Name: " ++ (name p) ++ ", email: " ++ (email p)
//
// instance Printable Integer where
//    format i = show i
object PrintableInstances {

  implicit val stringPrintable: Printable[String] =
    (value: String) => value

  implicit val personPrintable: Printable[Person] =
    (value: Person) => s"Name: ${value.name}, email: ${value.email}"

  implicit val integerPrintable: Printable[Integer] =
    (value: Integer) => value.toString

  implicit val intPrintable: Printable[Int] =
    (value: Int) => value.toString

  //
  // This is an abstract method the signature matches the format method in the Printable trait
  implicit val catPrintable: Printable[Cat] =
    (value: Cat) => s"${value.name} is a ${value.age} year old ${value.colour} cat"

  implicit def optionPrintable[A](implicit fmt: Printable[A]): Printable[Option[A]] =
    {
      case Some(a) => fmt.format(a)
      case None => "None"
    }

}

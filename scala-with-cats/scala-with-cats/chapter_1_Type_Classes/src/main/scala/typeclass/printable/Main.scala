
package typeclass.printable

import PrintableInstances._
import PrintableSyntax._
import common.{Cat, Person}

object Main extends App {
  //  
  // print is method in object Printable.
  //
  // def print[A](value: A)(implicit p: Printable[A]): Unit = println(p.format(value))
  //
  // It accepts a value of type A (common.Person in this case) and a implicit
  // second parameter of type Printable[A].
  // Because the second parameter is marked as implicit the compiler
  // looks for a definition of the trait Printable[common.Person] that is
  // a) In scope and b) marked as implicit.
  // It finds one in the PrintableInstances object: -
  //
  //implicit val personPrintable: Printable[common.Person] =
  //  new Printable[common.Person] {
  //    def format(value: common.Person): String = s"Name: ${value.name}, email: ${value.email}"
  //  }
  //
  // It can therefore 'fill in' the missing parameter at the call site like so: -
  //
  // print(common.Person("Martin","martin@gmail"))(personPrintable)
  //
  print(Person("Martin","martin@gmail"))
  //
  // The common.Person class does not have a print method.
  // Therefore the compiler looks for a class that is
  // a) marked as implicit and b) in scope
  // that accepts a type parameter and contains a method called print that
  // accepts an implicit parameter of Printable[A].
  //
  // The compiler can therefore use the following code at the call site: -
  //
  // PrintableOps(common.Person("Martin","email")).print(personPrintable)
  //
  Person("Martin","martin@gmail").print

  print("10")
  "10".print

  print(Integer.valueOf(1000))
  Integer.valueOf(1000).print
  1000.print

  val c = Cat("Puskin",5,"Gray")
  print(c)
  c.print

  //
  // implicit resolution is recursive!
  // There is no print method in the Option class.
  // Therefore the compiler looks for a class that is 
  // a) marked as implicit and b) in scope
  // that accepts a type parameter and contains a method called print that 
  // accepts an implicit parameter of Printable[Option[A]].
  // It also resolves the type inside the Option (common.Cat in this case) recursively!!
  //
  // PrintableOps(Option(Cats("Mad common.Cat",8,"white"))).print(optionPrintable(catPrintable))
  //
  Option(Cat("Mad cat",8,"white")).print
  //
  // This is a contravariance scenario.
  // Note that Some is a subclass of Option and that the 
  // below only works because the Printable trait has been marked as 
  // contravariant.
  // trait Printable[-A]
  //
  Some(Cat("Mad cat",8,"white")).print
}

//
// This is our type class
//
trait Print[A] {
  def print(value: A): String
}

//
// Syntax for our type class
//
object PrintSyntax {
  implicit class PrintOps[A](value: A) {
    def format(implicit p: Print[A]): String = p.print(value)
    def print(implicit p: Print[A]): Unit = println(p.print(value))
  }
}


final case class Cat(name: String, age: Int, colour: String)

//
// Instance of the type class for our Cat type.
// Because this is defined in the companion object, this is automatically
// in scope.
object Cat {
  implicit val catPrint: Print[Cat] = {
    (c: Cat) => s"${c.name} is a ${c.age} year old ${c.colour} cat."
  }
}


import PrintSyntax._
import Cat.catPrint

// Implicit
Cat("tiddles",20,"white").print
// Explicit
new PrintOps[Cat](Cat("tiddles",20,"white")).print(catPrint)


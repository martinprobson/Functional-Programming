
package typeclass.printable


// This is our type class
//
// In Haskell this would be: -
//
// class Printable a where
//     format :: a -> String
//
trait Printable[-A] {
  def format(value: A): String
}



object Printable {
  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  def print[A](value: A)(implicit p: Printable[A]): Unit = println(p.format(value))
}


object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    
    def format(implicit p: Printable[A]): String = p.format(value)

    def print(implicit p: Printable[A]): Unit = println(p.format(value))
  }
}


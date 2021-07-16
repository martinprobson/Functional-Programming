import Maybe.Exc

//
// This is our 'Try' class
//
sealed trait Maybe[+A]
final case class Raise(e: Exc) extends Maybe[Nothing]
final case class Return[A](a: A) extends Maybe[A]

object Maybe {
  type Exc = String
}

sealed trait Term
final case class Con(i: Int) extends Term
final case class Div(x: Term, y: Term) extends Term


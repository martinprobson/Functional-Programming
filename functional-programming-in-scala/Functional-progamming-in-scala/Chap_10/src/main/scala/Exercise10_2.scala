//
// Exercise 10.2
// Give a Monoid instance for combining Option instances
//
object Exercise10_2 {

  //
  // Note we could also say a2 else a1 here and still satisfy the
  // monoid rule even though the statements are not logically
  // equivalent.
  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def op(a1: Option[A], a2: Option[A]): Option[A] = {
      a1 orElse a2
    }

    def zero: None.type = None
  }

  //
  // We can get the dual of a monoid by flipping the 'op'.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(x: A, y: A): A = m.op(y,x)
    val zero: A = m.zero
  }

  //
  // Then we can have both versions of the Monoid on hand...
  //
  def firstOptionMonoid[A]: Monoid[Option[A]] = optionMonoid[A]
  def lastOptionMonoid[A]: Monoid[Option[A]] = dual(firstOptionMonoid)

}

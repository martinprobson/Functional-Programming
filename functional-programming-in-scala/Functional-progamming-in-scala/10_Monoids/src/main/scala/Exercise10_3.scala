
//
// Exercise 10.3
// A function having the same argument and return type is called an endofunction.
// Create a Monoid instance of an endofunction.
//
object Exercise10_3 {

  //
  // Note we could also say a1 andThen a2 here and still satisfy the
  // monoid rule even though the statements are not logically
  // equivalent.
  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    def op(a1: A => A, a2: A => A): A => A = {
      a1 compose a2
    }

    def zero: A => A = (a: A) => a
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
  def firstendoMonoid[A]: Monoid[A => A] = endoMonoid[A]
  def lastendoMonoid[A]: Monoid[A => A] = dual(firstendoMonoid)

}

//
// This is our `State` type
//
object State {
  type State[A, S] = S => (A, S)

  def unit[A, S](a: A): State[A, S] = s => (a, s)

  // Make State more like a Monad (single type parameter)
  type M2[A] = State[A, Int]
}

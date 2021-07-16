
/*
  A Monoid consists of the following: -
  - Some type `A`
  - An associative binary operation, `op`, that takes two values of type A and combines them into one: `op(op(x,y),z) == op(x,op(y,z)` for any choice of `x: A, y: A, z: A`
  - A value, `zero: A`, that is the identity for that operation: `op(x, zero) == x` and `op(zero, x) == x` for any `x: A`
*/
trait Monoid[A] {
  def op(a1: A, a2: A): A       // Associative binary operation
  def zero: A                   // The identity operation
}

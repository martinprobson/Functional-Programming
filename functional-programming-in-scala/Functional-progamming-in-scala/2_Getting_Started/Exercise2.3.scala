// vim:ts=2:sw=2:sts=2:et:syntax=scala
//
// Functional Programming in Scala - Exercise 2.3
//
// Currying - Convert a function f of two arguments into a function of one
//            argument that partially applies f.
//
//def curry[A,B,C](f: (A,B) => C): A => (B => C) =
//  (a: A) =>  (b: B) =>  f(a,b)
def curry[A,B,C](f: (A,B) => C): A => (B => C) =
  a =>  b =>  f(a,b)

val curryAdd = curry( (a: Int, b: Int) => a + b)
val curryMult = curry( (a: Int, b: Int) => a * b)

curryAdd(3)(2)
curryMult(4)(2)



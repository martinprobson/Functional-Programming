// vim:ts=2:sw=2:sts=2:et:syntax=scala
//
// Functional Programming in Scala - Exercise 2.4
//
// UnCurry  - Implement uncurry which reverses the transformation of curry
//
def curry[A,B,C](f: (A,B) => C): A => (B => C) =
  a => b =>  f(a,b)


def uncurry[A,B,C](f: A => (B => C) ): (A,B) => C =
  (a,b) => f(a)(b)

val add = (a: Int, b: Int) => a + b

val curryAdd = curry(add)
val unCurryAdd = uncurry(curriedAdd) 

curryAdd(1)(2)

unCurryAdd(2,2)

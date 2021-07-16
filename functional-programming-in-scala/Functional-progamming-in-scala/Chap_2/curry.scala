
def curry[A,B,C](f: (A,B) => C): A => (B => C) =
  (a: A) =>  (b: B) =>  f(a,b)


def uncurry[A,B,C](f: A => (B => C) ): (A,B) => C =
  (a: A, b: B) => f(a)(b)

def partial1[A,B,C](a: A, f: (A,B) => C): B => C = 
  (b: B) => f(a,b)

val add = (a: Int, b: Int) => a + b

val curriedAdd = curry(add)

val partialAdd1 = partial1(1,add)

val unCurriedAdd = uncurry(curriedAdd) 

curriedAdd(1)(2)

unCurriedAdd(1,2)

partialAdd1(1)

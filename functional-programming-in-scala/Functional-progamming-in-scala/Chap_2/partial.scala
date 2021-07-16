//
// partial1 is a function that accepts two parameters: -
//  - a of type A
//  - function that accepts types A,B and returns C
//
//  The function returns a function that accepts B and returns C.
//
//
def partial1[A,B,C](a: A, f: (A,B) => C): B => C = 
  b => f(a,b)


val add1 = partial1(1, (a: Int, b: Int) => a + b)

println(add1(10))

val prefixHello = partial1("Hello ",(a: String, b: String) => a + b)

println(prefixHello("Martin"))

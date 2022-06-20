// vim:ts=2:sw=2:sts=2:et:syntax=scala
//
// Functional Programming in Scala - Exercise 2.5
//
// Implement a higher order function that composes two functions.
//

def compose[A,B,C](f: B => C, g: A => B): A => C = 
  (a: A) => f(g(a))

def square(x: Int) = x * x
def cube(x: Int) = x * x * x
def hello(s: String): String = "Hello" + s
def reverse(s: String): String = s.reverse
def addList(l: List[Int]): List[Int] = l.map(_ + 2)
def filterList(l: List[Int]): List[Int] = l.filter( _ < 4)

val l = compose(filterList,addList)
l(List(1,2,3))

val a = compose(square,cube)
a(2)

val s = compose(hello,reverse)
s("Martin")

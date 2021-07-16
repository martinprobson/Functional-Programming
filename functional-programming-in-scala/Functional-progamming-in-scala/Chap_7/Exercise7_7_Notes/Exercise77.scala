


object Exercise77 extends App {
  
  println("Start")
  
  def r[A](a: List[A]): List[A] = a
  
  def map[A,B](x: List[A])(f: A => B): List[B] =
    x.foldRight(Nil:List[B]){ (a,b) => f(a) :: b}
  
  val r1: Function1[Int,Int] = a => a+1
  
  def reverse[A](a: List[A]): List[A] = a.reverse
  
  assert(map(reverse(List(1,2,3))){_ + 1} == reverse(map(List(1,2,3)){_+1}) )

  def mapI(x: List[Int])(f: Int => Int): List[Int] =
    x.foldRight(Nil:List[Int]){ (a,b) => f(a) :: b}

  def reverseI(a: List[Int]): List[Int] = reverse(a)

  def plusOne(a: List[Int]): List[Int] = 
    map(a){_+1}
  
  def f = plusOne _ compose reverseI _
  def g = reverseI _ compose plusOne _
  
  def tail[A](a: List[A]): List[A] = a.tail
  
  def tailI(a: List[Int]): List[Int] = tail(a)
  
  def f1 = plusOne _ compose tailI _
  def g1 = tailI _ compose plusOne _
  
  assert(f(List(1,2,3)) == g(List(1,2,3)))
  assert(f1(List(1,2,3)) == g1(List(1,2,3)))
  
//  val g = reverse _ compose map _ {_+1}
  val res1 = f(List(1,2,3))
  println(s"res1 = ${res1}")
  val res2 = g(List(1,2,3))
  println(s"res2 = ${res2}")

  val res3 = f1(List(1,2,3))
  println(s"res3 = ${res3}")
  val res4 = g1(List(1,2,3))
  println(s"res4 = ${res4}")

//  def f(x: Int): Int = {
//    println("Inside function f")
//    x + 1
//  }
//  def g(x: Int): Int = {
//    println("Inside function g")
//    x + 10
//  }
//  
//  val f1 = f _ compose g _
//  val f2 = g _ compose f _
//  f1(1)
//  f2(1)
}

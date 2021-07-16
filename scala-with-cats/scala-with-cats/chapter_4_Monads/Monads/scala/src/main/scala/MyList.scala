
import scala.annotation.tailrec
import cats.Eq
import Monad._


sealed trait MyList[+A]

case object Nil extends MyList[Nothing]

case class Cons[A](h: A, tl: MyList[A]) extends MyList[A]

object MyList {

  //
  // implicit (Cats) Eq for List
  //
  implicit def listEqual[A]: Eq[MyList[A]] =
    Eq.instance[MyList[A]] { (l1, l2) => equal(l1,l2) }

  def flatMap[A,B](l: MyList[A])(f: A => MyList[B])(implicit m: Monad[MyList]): MyList[B] =
    m.flatMap(l)(f)

  def map[A,B](l: MyList[A])(f: A => B)(implicit m: Monad[MyList]): MyList[B] =
    m.map(l)(f)
  //
  // Equal method
  //
  def equal[A](l1: MyList[A], l2: MyList[A]): Boolean = (l1, l2) match {
    case (Nil, Nil) => true
    case (Cons(x1,xs1), Cons(x2,xs2)) => if (x1 == x2) equal(xs1,xs2) else false
    case (_,_) => false
  }
  //
  //  head method
  //
  def head[A](l: MyList[A]): A = l match {
    case Nil => throw new java.util.NoSuchElementException("head of an empty list")
    case Cons(h,_) => h
  }

  @tailrec
  def foldLeft[A,B](l: MyList[A], z: B)(f: (B,A) => B): B = l match  {
    case Nil => z
    case Cons(h,tl) => foldLeft(tl,f(z,h))(f)
  }

  // tail recursive foldRight
  // written in terms of foldLeft and reverse.
  def foldRight[A,B](l: MyList[A], z: B)(f: (B,A) => B): B =
      foldLeft(MyList.reverse(l),z)(f)

  def append[A](l1: MyList[A], l2: MyList[A]): MyList[A] =
    foldRight(l1,l2){ (b,a) => Cons(a,b) }
  //
  //  foreach method
  //
  def foreach[A](l: MyList[A])(f: (A) => Unit) {
    l match {
      case Nil => Unit
      case Cons(h,tl) => f(h); foreach(tl)(f)
    }
  }

  def reverse[A](l: MyList[A]): MyList[A] =
    foldLeft(l,Nil:MyList[A]){ (a, b) => Cons(b,a)}

  def apply[A](as: A*): MyList[A] = {
    if (as.isEmpty) Nil
    else Cons(as.head,apply(as.tail:_*))
  }

  //
  // Utility function to build a list of length n of type
  // returned by factory function.
  //
  def buildList[A](n: Int)(factory: Int => A): MyList[A] = {
    @tailrec
    def _build(n: Int, l: MyList[A]): MyList[A] = n match {
      case 0 => l
      case n => _build(n - 1,Cons(factory(n),l))
    }
    _build(n,MyList[A]())
  }
}

import MyList._
object Run extends App {
    println("Hello")
    val l = MyList(1,2,3)
    println(l)
    val l2 = map(l)(_*2)
    println(l2)
    val l3 = flatMap(l2)(a => MyList(a * 10))
    println(l3)
}

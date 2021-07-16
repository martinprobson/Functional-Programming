package fpinscala.datastructures

//
// Functional Programming in Scala
// Chapter 3 - Functional Data Structures
//

import scala.annotation.tailrec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grizzled.slf4j.Logging


sealed trait List[+A]

case object Nil extends List[Nothing] 

case class Cons[A](h: A, tl: List[A]) extends List[A]

object List {

  //
  //  head method
  //
  def head[A](l: List[A]): A = l match {
    case Nil => throw new java.util.NoSuchElementException("head of an empty list")
    case Cons(h,_) => h
  }

  // 
  //  foreach method
  //
  def foreach[A](l: List[A])(f: (A) => Unit) {
    l match {
      case Nil => Unit
      case Cons(h,tl) => f(h); foreach(tl)(f)
    }
  }
  //
  // map - tail recursive version
  //
  // implementation in terms of foldRight
  //
  def map[A,B](l: List[A])(f: A => B): List[B] = 
      List.foldRight(l,Nil:List[B]){ (b,a) => Cons(f(a),b)}
  //
  // another implementation of map
  //
  def map2[A,B](l: List[A])(f: A => B): List[B] = l match {
    case Nil => Nil
    case Cons(h,tl) => Cons(f(h),map2(tl)(f))
  }

  //  non tail recursive foldRight
  def foldRight_NTR[A,B](l: List[A], seed: B)(f: (A,B) => B): B = l match {
    case Nil => seed
    case Cons(h,tl) => f(h,foldRight_NTR(tl,seed)(f))
  }

  //  non tail recursive foldLeft
  def foldLeft_NTR[A,B](l: List[A], seed: B)(f: (B,A) => B): B = l match {
    case Nil =>  seed
    case Cons(h,tl) => f(foldLeft_NTR(tl,seed)(f),h)
  }
  //
  //
  // Exercise 3.10
  //
  // tail recursive foldLeft
  @tailrec
  def foldLeft[A,B](l: List[A], z: B)(f: (B,A) => B): B = l match  {
    case Nil => z 
    case Cons(h,tl) => foldLeft(tl,f(z,h))(f) 
  }

  //
  //  Exercise 3.15 - Write a function that concatenates a list
  //                  of lists into a single list. Try to use the
  //                  functions we have already defined.
  //
  def flattenList[A](lol: List[List[A]]): List[A] =
    foldLeft(lol,Nil:List[A]){ (l1,l2) => append(l1,l2) }


  //
  // Exercise 3.13 - A tail recursive foldRight
  //
  // tail recursive foldRight
  // written in terms of foldLeft and reverse.
  def foldRight[A,B](l: List[A], z: B)(f: (B,A) => B): B =
      foldLeft(List.reverse(l),z)(f)


  def concat(sl: List[String]) = foldLeft(sl,"")(_+_)

  //
  // Exercise 3.11 - Write sum, product and length using
  //                 foldLeft
  //
  // sum using foldLeft
  def sum(ints: List[Int]) = foldLeft(ints,0)(_+_)

  // product using foldLeft
  def product(ds: List[Double]) = foldLeft(ds,1.0)(_*_)
    
  //
  // length using foldLeft
  //
  def length[A](l: List[A]): Int = foldLeft(l,0){ (c,_) => c+1}

  def apply_TR[A](as: A*): List[A] = {
    @tailrec
    def _apply[A](accum: List[A],as: A*): List[A] =  
      if (as.isEmpty) accum
      else _apply(Cons(as.head,accum),as.tail:_*)

    reverse(_apply(Nil: List[A],as:_*))
  }

  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nil
    else Cons(as.head,apply(as.tail:_*))
  }


  //
  //
  // Exercise 3.2
  //
  def tail[A](l: List[A]): List[A] = l match {
    case Nil => throw new java.lang.UnsupportedOperationException("Tail of an empty list")
    case Cons(_,t) => t
  }

  //
  // Exercise 3.3
  //
  def setHead[A](h: A, l: List[A]): List[A] = l match {
    case Nil => List(h)
    case Cons(_,t) => Cons(h,t)
  }

  //
  // Exercise 3.4
  //
  @tailrec
  def drop[A](l: List[A], n: Int): List[A] = {
    require( n >= 0, "n must not be negative")
    if (n == 0) l
    else l match {
      case Nil => throw new java.lang.UnsupportedOperationException("drop of an empty list")
      case Cons(_,t) => drop(t,n -1)
    }
  }

  //
  // Exercise 3.5
  //
  @tailrec
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(h,t) => if (f(h)) dropWhile(t,f) else l
  }

  //
  // Exercise 3.12 - Reverse a list
  //
  def reverse[A](l: List[A]): List[A] = 
    foldLeft(l,Nil:List[A]){ (a,b) => Cons(b,a)}

  //
  // Exercise 3.14 - append implemented using foldRight
  //
  def append[A](l1: List[A], l2: List[A]): List[A] = 
    foldRight(l1,l2){ (b,a) => Cons(a,b) }

  //
  // Exercise 3.6
  //
  def init[A](l: List[A]): List[A] = {
    @tailrec
    def _init(accum: List[A], l: List[A]): List[A] = l match {
      case Nil => Nil
      case Cons(x,Nil) => accum
      case Cons(x,xs) => _init(List.append(accum,List(x)),xs)
    }
    _init(Nil,l)
  }

  //
  // Utility function to build a list of length n of type
  // returned by factory function.
  //
  def buildList[A](n: Int)(factory: Int => A): List[A] = {
    @tailrec
    def _build(n: Int, l: List[A]): List[A] = n match {
      case 0 => l
      case n => _build(n - 1,Cons(factory(n),l))
    }
    _build(n,List[A]())
  }


  /* 
  *
  * Exercise 3.24 - Write a function hasSubseqence for checking 
  *                 whether a list contains another list as a 
  *                 subsequence.
  *
  */
  @tailrec
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {

    //
    // Returns the sublist that has the value 'f' as head or Nil
    // if not found.
    //
    @tailrec
    def findsl(l: List[A], f: A): List[A] = l match {
      case Nil => Nil
      case Cons(h,tl) => if (f == h) l 
                         else findsl(tl,f)
    }
    //
    // Slice a list
    //
    def slice[A](l: List[A], from: Int, to: Int): List[A] = {
      def _slice(l: List[A], from: Int, to: Int, pos: Int): List[A] = {
        if (pos < from)
          _slice(List.tail(l), from, to, pos + 1)
        else
          if ((pos >= from) && (pos <= to))
            Cons(List.head(l), _slice(List.tail(l), from, to, pos + 1))
          else
            Nil
      }
      _slice(l,from,if (to > List.length(l)) List.length(l) else to ,1)
    }

    //
    // hasSubsequence
    //
    if ((sub == Nil) || (sup == sub))
      true
    else {
      val sl = findsl(sup,List.head(sub))
      if (sl == Nil)
        false
      else
        if (slice(sl,1,List.length(sub)) == sub)
          true
        else
          hasSubsequence(List.tail(sl),sub)
      }
  }

  /*
  * Exercise 3.22 - Write a function that accepts two lists and 
  *                 constructs a new list by adding corresponding
  *                 elements.
  *                 For example, List(1,2,3) and List(4,5,6)
  *                 become List(5,7,9)
  *
  *   Add two lists of integers  - non tail recursive
  *
  */
  def addLists_NTR( l1: List[Int], l2: List[Int]): List[Int]  = (l1, l2) match {
      case (Nil,Nil) => Nil
      case (Nil,l2)  => l2
      case (l1,Nil)  => l1
      case (Cons(h1,Nil),Cons(h2,t2)) => Cons(h1 + h2,t2)
      case (Cons(h1,t1),Cons(h2,Nil)) => Cons(h1 + h2,t1)
      case (Cons(h1,t1),Cons(h2,t2)) => Cons(h1 + h2,addLists_NTR(t1,t2))
    }

  /*
  * Exercise 3.22 - Write a function that accepts two lists and 
  *                 constructs a new list by adding corresponding
  *                 elements.
  *                 For example, List(1,2,3) and List(4,5,6)
  *                 become List(5,7,9)
  *
  *  Add lists of integers - tail recursive
  *
  */
  def addLists(l1: List[Int], l2: List[Int]): List[Int] = {
    @tailrec
    def _addLists( l1: List[Int], l2: List[Int], accum: List[Int]): List[Int]  = (l1, l2) match {
        case (Nil,Nil) => Nil
        case (Nil,l2)  => l2
        case (l1,Nil)  => l1
        case (Cons(h1,Nil),Cons(h2,t2)) => List.append(accum,Cons(h1 + h2,t2))
        case (Cons(h1,t1),Cons(h2,Nil)) => List.append(accum,Cons(h1 + h2,t1))
        case (Cons(h1,t1),Cons(h2,t2)) => _addLists(t1,t2,List.append(accum,List(h1 + h2)))
      }
    _addLists(l1,l2,Nil)
  }

  /*
  * Exercise 3.23 - Generalize the addLists function so that it is
  *                 not specific to integers or addition.
  *                 Name your generalized function zipWith.
  *
  */
  def zipWith[A](l1: List[A], l2: List[A])(f: (A,A) => A): List[A] = {
    @tailrec
    def _zipWith( l1: List[A], l2: List[A], accum: List[A])(f: (A,A) => A): List [A]  = (l1, l2) match {
        case (Nil,Nil) => Nil
        case (Nil,l2)  => l2
        case (l1,Nil)  => l1
        case (Cons(h1,Nil),Cons(h2,t2)) => List.append(accum,Cons(f(h1,h2),t2))
        case (Cons(h1,t1),Cons(h2,Nil)) => List.append(accum,Cons(f(h1,h2),t1))
        case (Cons(h1,t1),Cons(h2,t2)) => _zipWith(t1,t2,List.append(accum,List(f(h1,h2))))(f)
      }
    _zipWith(l1,l2,Nil)(f)
  }


}

object Run extends App with Logging {
    //
    // Exercise 3.1 - What wil be the result of the following match expression?
    //
    val x = List(1,2,3,4,5) match {
      case Cons(x,Cons(2,Cons(4,_))) => x               // no match 3 != 4
      case Nil => 42                                    // no match List is not Nil
      case Cons(x,Cons(y,(Cons(3,Cons(4,_))))) => x + y // match => 3 (x + y)
      case Cons(h,t) => h + List.sum(t)                 // not checked already matched
      case _ => 101                                     // not checked alreay matched
    }
    assert(x == 3)

    def add(x: Int, y: Int) = {
      trace(s"add($x,$y)")
      x + y
    }

    def toStr(b: String, a: Int) = {
      trace(s"""toStr('$b','$a')""")
      b + " " + a.toString
    }


//    println("foldLeft")
//    List.foldLeft(List(1,2,3,4,5,6,7,8,9,10),0) { add(_,_) }
    println("foldRight")
    List.foldRight_NTR(List(1,2,3),0) { add(_,_) }
    println("foldLeft_NTR")
    List.foldLeft_NTR(List(1,2,3),0) { add(_,_) }
    println("foldLeft")
    List.foldLeft(List(1,2,3),0) { add(_,_) }

//    println("foldLeft")
//    List.foldLeft(List(1,2,3,4,5,6,7,8,9,10)," ") { toStr(_,_) }
//    println("foldRight")
//    List.foldRight(List(1,2,3,4,5,6,7,8,9,10)," ") { toStr(_,_) }
}

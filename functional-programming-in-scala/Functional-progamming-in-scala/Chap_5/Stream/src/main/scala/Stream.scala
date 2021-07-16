
package fpinscala.chap5
//hide std library Stream
import scala.{Stream => _, _}
import scala.annotation.tailrec
import Stream.cons
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grizzled.slf4j.Logging
//
// Functional Programming in Scala
// Chapter 5 - Strictness and Laziness
//
sealed trait Stream[+A] {
  /*
  * Exercise 5.1 - Convert a Stream to a List
  *                This version is not tail recursive.
  */
  def toList_NTR: List[A] = this match {
    case Empty => List()
    case Cons(h,tl) => h() :: tl().toList
  }

  /*
  * tail recursive toList using ListBuffer.
  */
  def toList_TR: List[A] = {
    val lbuf = scala.collection.mutable.ListBuffer[A]()
    @tailrec
    def _toList(s: Stream[A]): List[A] = s match {
      case Empty => lbuf.toList
      case Cons(h,tl) => lbuf += h() 
                         _toList(tl())
    }
    _toList(this)
  }

  //
  // Use toList_TR
  //
  def toList: List[A] = 
    this.toList_TR
  

  /*
  * toList tail recursive version defined in terms of 
  * foldLeft.
  */
  def toList_FL: List[A] = 
    foldLeft(Nil: List[A]){ (b,a) => a :: b }.reverse

  /*
  * Exercise 5.2 - Implement take(n) and drop(n)
  */
  @tailrec
  final def drop(n: Int): Stream[A] = this match {
    case Cons(_,tl) if n > 0  => tl().drop(n -1)
    case _  => this
  }

  /*
  * Non tail recursive take
  */
  def take_NTR(n: Int): Stream[A] = this match {
    case Cons(h,tl) if n > 0 => cons(h(),tl().take_NTR(n -1))
    case _ => Empty
  }

  /*
  * Exercise 5.13 - Write take in terms of unfold.
  */
  def take_UF(n: Int): Stream[A] = 
    Stream.unfold( (this,n)){ case (s,n) => s match { case Cons(h,tl) if n > 0 => Some((h(),(tl(),n-1)))
                                                      case _ => None } }

  def take_TR(n: Int): Stream[A] = {
   @tailrec
   def _take(s: Stream[A], n: Int, accum: Stream[A]): Stream[A] = s match {
     case Cons(h,tl) if n > 0 => _take(tl(), n - 1,cons(h(),accum))
     case Cons(h,_)  if n == 1 => cons(h(),Empty)
     case _ => accum.reverse
   }
   _take(this,n,Empty)
  }

  def take(n: Int) = this.take_TR(n)

  def reverse: Stream[A] = 
    this.foldLeft(Empty: Stream[A]){ (a,b) => cons(b,a) }


  /*
  *  Exercise 5.3 - Implement function takeWhile to 
  *                 return all starting elements of a stream
  *                 that match the given predicate.
  */
  def takeWhile(p: A => Boolean): Stream[A] =  this match {
    case Empty => Empty
    case Cons(h,tl) => if (p(h())) cons(h(),tl().takeWhile(p))
                       else Empty
  }

  /*
  *  Exercise 5.5 - Use foldRight to implement takeWhile.
  *
  */
  def takeWhile_FR(p: A => Boolean): Stream[A] =  
    foldRight(Empty: Stream[A]){ (a,b) => if (p(a)) cons(a,b)
                                          else Empty }

  /*
  * Exercise 5.13 - Write takeWhile in terms of unfold.
  */
  def takeWhile_UF(p: A => Boolean): Stream[A] = 
    Stream.unfold(this){ case Cons(h,tl) if p(h()) => Some((h(),tl() ))
                         case _ => None }

  /*
  * foldLeft - tail recursive version
  */
  @tailrec
  final def foldLeft[B](seed: => B)(f: (=> B, A) => B): B = this  match {
    case Empty => seed 
    case Cons(h,tl) => tl().foldLeft(f(seed,h()))(f)
  }
    
  //
  //  foldRight
  //
  final def foldRight[B](seed: => B)(f: (A, => B) => B): B = this match {
    case Empty => seed
    case Cons(h,tl) => f(h(),tl().foldRight(seed)(f))
  }

  @tailrec
  final def exists(p: A => Boolean): Boolean = this match {
    case Cons(h,tl) => p(h()) || tl().exists(p)
    case _ => false
  }

  def exists_FR(p: A => Boolean): Boolean = 
    foldRight(false){ (a,b) => p(a) || b }

  def exists_FL(p: A => Boolean): Boolean =
    foldLeft(false){ (b,a) => b || p(a) }

  /*
  *  Exercise 5.4 - Implement function forAll, which checks 
  *                 that all elements in the stream match a 
  *                 given predicate. The traversal should be
  *                 terminated as soon as a non-matching value
  *                 is found.
  */
  def forAll(p: A => Boolean): Boolean = 
    foldRight(true){ (a,b) => p(a) && b }

  /*
  *  Exercise 5.6 - Implement headOption using foldRight.
  */
  def headOption: Option[A] = 
    foldRight(None: Option[A]) { (a,_) => Some(a) }
  
  /*
  * Exercise 5.7 - Implement map, filter, append and
  *                flatMap using foldRight.
  *
  * No point in having non-strict args for function here, as map has to 
  * traverse the whole stream anyway.....
  */
  def map[B](f: (A) => B): Stream[B] = 
      foldRight(Empty: Stream[B]){ (a,b) => cons(f(a),b)}
  

  /*
  * Exercise 5.13 - Write map in terms of unfold.
  */
  def map_UF[B](f: (A) => B): Stream[B] = 
    Stream.unfold( (this,Empty:Stream[B])){ case (a,b) => a match { case Cons(h,tl) => Some(f(h()),(tl(),b))
                                                                    case _ => None } }

  //
  // Exercise 5.7 - filter
  //
  def filter(f: A => Boolean): Stream[A] = 
    foldRight(Empty:Stream[A]){ (a,b) => 
                                   if (f(a)) 
                                     cons(a,b) 
                                   else b }

  //
  // Exercise 5.7 - flatMap
  //
  def flatMap[B](f: A => Stream[B]): Stream[B] = 
    foldRight(Empty: Stream[B]){ (a,b) => f(a) append b }
  
  //
  // Exercise 5.7 - append
  //
  def append[B >: A](l2: => Stream[B]): Stream[B] = 
    this.foldRight(l2){ (h,t) => cons(h,t) }

  /*
  * Exercise 5.13 - Write zipWith using unfold
  */
  def zipWith[B >: A](s2: Stream[B])(f: (B,B) => B): Stream[B] =
    Stream.unfold( (this,s2)){ 
      case (Empty,Empty)             => None
      case (Empty,Cons(h2,t2))       => Some((h2(),(Empty,t2())))
      case (Cons(h1,t1),Empty)       => Some((h1(),(t1(),Empty)))
      case (Cons(h1,t1),Cons(h2,t2)) => Some((f(h1(),h2()),(t1(),t2())))
    }

  /*
  * Exercise 5.13 - zipAll function
  */
  def zipAll[B](s2: Stream[B]): Stream[(Option[A],Option[B])] =
    Stream.unfold( (this,s2)){
      case (Empty,Empty)             => None
      case (Empty,Cons(h2,t2))       => Some((None,Some(h2())),(Empty,t2()))
      case (Cons(h1,t1),Empty)       => Some((Some(h1()),None),(t1(),Empty))
      case (Cons(h1,t1),Cons(h2,t2)) => Some((Some(h1()),Some(h2())),(t1(),t2()))}


  /*
  *  Exercise 5.14 - startsWith implemented as a standalone function
  */
  def startsWith_SO[A](sub: Stream[A]): Boolean = (this,sub) match {
    case (Empty,Empty)             => true
    case (_,Empty)                 => true
    case (Empty,_ )                => false
    case (Cons(h1,t1),Cons(h2,t2)) => if (h1() == h2()) t1().startsWith_SO(t2())
                                      else false
  }

  /*
  *  Exercise 5.14 - startsWith implemented in terms of existing functions
  *  (in this case zipAll, takeWhile, map and forAll.
  */
  def startsWith[B](sub: Stream[B]): Boolean = 
    this.zipAll(sub)
        .takeWhile{ case (_,b) => b != None}
        .map{ case ((Some(x),Some(y))) => x == y
              case _ => }
        .forAll(_ == true)

  /*
  *  Exercise 5.15 - Implement tails using unfold.
  */
  def tails: Stream[Stream[A]] =
    Stream.unfold(Option(this)){ case None => None
                                 case Some(Empty) => Some((Empty,None))
                                 case Some(Cons(h,tl)) => Some( (cons(h(),tl()),Some(tl())))}

  /*
  *  We can now implement hasSubSequence
  */
  def hasSubSequence[B](s: Stream[B]): Boolean =
    tails exists (_ startsWith s)

} 

case object Empty extends Stream[Nothing]
//
// For technical reasons, we cannot use the usual non-strict Scala syntax here.
// If we try scala will complain with 'val parameters may not be call by name`
//
case class Cons[+A](h: () => A, tl: () => Stream[A]) extends Stream[A]

object Stream  
{
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] = {
    if (as.isEmpty) empty
    else cons(as.head,apply(as.tail:_*))
  }

  //
  // Utility function to build a Stream of length n of type
  // returned by factory function.
  //
  def buildStream[A](n: Int)(factory: Int => A): Stream[A] = {
    @tailrec
    def _build(n: Int, s: Stream[A]): Stream[A] = n match {
      case 0 => s
      case n => _build(n - 1,cons(factory(n),s))
    }
    _build(n,Stream[A]())
  }

  /*
  * Exercise 5.8 - Generate a infinite stream of the given value
  */
  def constant[A](a: A): Stream[A] = cons(a,constant(a))

  /*
  * Exercise 5.9 - Write a function that generates an infinite stream of
  *                integers, starting from n, then n+1 , n+2 etc.
  */
  def from(n: Int): Stream[Int] = cons(n,from(n + 1))

  /*
  * Exercise 5.10 - Write a function fibs that generates the infinite
  *                 stream of Fibonacci numbers.
  */
  def fibs: Stream[Double] = {
    def _fibs(n1: Double,n2: Double): Stream[Double] =
      cons(n1,_fibs(n2,n1+n2))
    _fibs(0,1)
  }

  /*
  *  Exercise 5.11 - Write a more general Stream building function
  *                  called unfold. It takes an initial state, and
  *                  a function for producing both the next state and
  *                  the next value in the generated stream.
  */
  def unfold[A,S](z: S)(f: S => Option[(A,S)]): Stream[A] = f(z) match {
    case Some((n,z)) => cons(n, unfold(z)(f))
    case None => Empty
  }

  /*
  * Exercise 5.12 - Write fibs, from, constant and ones in terms
  *                 of unfold
  */
  def fibs_UF: Stream[Double] = 
    unfold((0.0,1.0)){ case (a,s) => Some(a,(s,a+s))}

  def from_UF(n: Int): Stream[Int] =
    unfold(n){ (i: Int) => Some((i,i+1)) }

  def constant_UF[A](a: A): Stream[A] =
    unfold(a){ (a: A) => Some((a,a)) }

}

object Run extends App with Logging {

    
  def add(x: Int, y: Int) = {
      trace(s"add$x($y)")
      x + y
    }

    def add10(y: Int) = add(10,y)
    def add20(y: Int) = add(20,y)
    def add30(y: Int) = add(30,y)
    def add40(y: Int) = add(40,y)
    def add50(y: Int) = add(50,y)

    def filter(n: Int, x: Int) = {
      trace(s"filter$n($x)")
      x > n
    }

    def filter10(y: Int) = filter(10,y)
    def filter20(y: Int) = filter(20,y)
    def filter30(y: Int) = filter(30,y)
    def filter40(y: Int) = filter(40,y)
    def filter50(y: Int) = filter(50,y)

//    val s = Stream(1,2,3,4).map(add10)
//                           .filter(filter10)
//                           .map(add20)
//                           .filter(filter20)
//                           .map(add30)
//                           .filter(filter30)
//                           .map(add40)
//                           .filter(filter40)
//                           .map(add50)
//                           .filter(filter50)
//                            
    val s = Stream(1,2,3,4).map(add10).map(add20)
    trace("TOLIST")
    s.toList

    Stream.constant(1).take(1000000).toList
}


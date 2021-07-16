import cats.Eval

import scala.annotation.tailrec

val now = math.random + 1000
lazy val later = math.random + 1000
def always = math.random + 1000
now
later
always
now
later
always

val evalNow = Eval.now(math.random + 1000)
val evalLater = Eval.later(math.random + 1000)
val evalAlways = Eval.always(math.random + 1000)

evalNow.value
evalLater.value
evalAlways.value
evalNow.value
evalLater.value
evalAlways.value

/*
 The memoize means we always get the same two random
 numbers back on a call to r.value
 */
val r = Eval.always { println("step1"); math.random + 1000}
  .map { i => println("step2"); (i,math.random + 1000)}
  .memoize
r.value
r.value

val r2 = r.map{ t => (t._1,t._2,math.random + 1000)}
r2.value
r2.value

def foldRight_TR[A,B](as: scala.collection.immutable.List[A], acc: B)(fn: (A,B) => B): B = {
  @tailrec
  def _foldRight_TR[A,B](as: scala.collection.immutable.List[A], acc: B)(fn: (A,B) => B): B =
    as match {
      case head :: tail => {
        val t = fn(head,acc)
        _foldRight_TR(tail, t)(fn)
      }
      case scala.collection.immutable.Nil =>
        acc
    }
  _foldRight_TR(as.reverse,acc)(fn)
}
//@tailrec
def foldRight[A,B](as: scala.collection.immutable.List[A], acc: B)(fn: (A,B) => B): B =
  as match {
    case head :: tail =>
      fn(head, foldRight(tail,acc)(fn))
    case scala.collection.immutable.Nil =>
      acc
  }

def foldRightE[A,B](as: scala.collection.immutable.List[A], acc: B)(fn: (A,B) => B): Eval[B] =
  as match {
    case head :: tail =>
      Eval.defer(foldRightE(tail,acc)(fn).map(fn(head,_)))
    case scala.collection.immutable.Nil =>
      Eval.now(acc)
  }
foldRightE(scala.collection.immutable.List(1,2,3),0)( (i: Int,a: Int) => a + i).value
foldRightE(Range(1,1000000).toList,0)( (i:Int, a: Int) => a + i).value

import scala.math.BigInt
foldRight_TR(scala.collection.immutable.List(1,2,3),0)( (i: Int,a: Int) => a + i)
foldRight_TR(scala.collection.immutable.List("Martin","Peter","Robson"),"")( (s: String, a: String) => a + s)
foldRight(scala.collection.immutable.List("Martin","Peter","Robson"),"")( (s: String, a: String) => a + s)
//foldRight(List("Martin","Peter","Robson"),"")( (s: String, a: String) => a + s)

def factorial(n : BigInt): Eval[BigInt] =
  if (n == 1)
    Eval.now(n)
  else
    Eval.defer(factorial(n - 1).map(_*n))

factorial(50000).value

//def foldRight2[A,B](as: scala.collection.immutable.List[A], acc: B)(fn: (A,B) => B): Eval[B] =
//  as match {
//    case head :: tail =>
//      Eval.now(fn(head, Eval.defer(foldRight2(tail,acc)(fn))))
//    case scala.collection.immutable.Nil =>
//      Eval.now(acc)
//  }

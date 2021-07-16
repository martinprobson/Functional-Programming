
def sum(ints: List[Int]): Int = ints match {
case Nil => 0
case Cons(x,xs) => x + sum(xs)
}

def product(ds: List[Double]): Double = ds match {
case Nil => 1.0
case Cons(0.0,_) => 0.0
case Cons(x,xs) => x * product(xs)
}

def apply[A](as: A*): List[A] = {
if (as.isEmpty) Nil
else Cons(as.head,apply(as.tail:_*))
}

def tail[A](l: List[A]): List[A] = l match {
case Nil => throw new java.lang.UnsupportedOperationException("Tail of an empty list")
case Cons(_,t) => t
}

def setHead[A](h: A, l: List[A]): List[A] = l match {
case Nil => List(h)
case Cons(_,t) => Cons(h,t)
}

@tailrec
def drop[A](l: List[A], n: Int): List[A] = {
require( n >= 0, "n must not be negative")
if (n == 0) l
else l match {
  case Nil => throw new java.lang.UnsupportedOperationException("drop of an empty list")
  case Cons(_,t) => drop(t,n -1)
}
}

@tailrec
def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
case Nil => Nil
case Cons(h,t) => if (f(h)) dropWhile(t,f) else l
}


//////////////////////////////////////////
//
//
//////////////////////////////////////////
//
//
func: (A,A) => A
seed value (sum is 0 product is 1.0)

List[A]

foldLeft(l: List[A], seed: A, f: (A,A) => A   ): A = l match {
  case Nil =>  seed
  case (h,tl) => f(h,foldLeft(tl,seed,f)
}

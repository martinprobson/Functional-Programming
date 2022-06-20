// vim:ts=2:sw=2:sts=2:et:syntax=scala
//
// Functional Programming in Scala - Exercise 2.1
//
import scala.annotation.switch
import scala.annotation.tailrec
import scala.math.BigInt

//
// fib1 is a tree-recursive version of Fibonacci number generator.
// It is very inefficient as it cannot be tail optimized and repeated
// calculations of the same number occur in multiple branches in the tree.
//
def fib1(n: Int): Int = {
//  @annotation.tailrec
  if (n == 0) 0
  else if (n == 1) 1
  else fib1(n-2) + fib1(n-1)
}


//
// An iterative version of the Fibonacci number generator
//
def fibI(n: Int): Int = {
  var acc, prev = 0
  for (i <- 0 to n) {
    (i: @switch) match {
      case 0 => acc = 0; prev = 0
      case 1 => acc = 1; prev = 0
      case _ => { val p = acc
                  acc = acc + prev
                  prev = p
                }
    }
  }
  acc
}


//
// The answer to exercise 2.1 - A tail optimized recursive version
//
def fib(n: Int): Int  = {
  @tailrec
  def go(n: Int, acc: Int, prev: Int): Int = {
    if (n == 0) 
      prev
    else
      go(n-1,acc + prev,acc)
  }
  go(n,1,0)
}

//
// Same as above with BigInts
//
def fibBig(n: Int): BigInt  = {
  @tailrec
  def go1(n: Int, acc: BigInt, prev: BigInt): BigInt = {
    if (n == 0) 
      prev
    else
      go1(n-1,acc + prev,acc)
  }
  go1(n,BigInt(1),BigInt(0))
}

//
// Test
//
val FibNumbers = Map( 0  -> 0,
                      1  -> 1,
                      2  -> 1,
                      3  -> 2,
                      4  -> 3,
                      5  -> 5,
                      6  -> 8,
                      7  -> 13,
                      8  -> 21,
                      9  -> 34,
                      10 -> 55,
                      45 -> 1134903170
                    )
FibNumbers.foreach {
  case(pos,num) => { assert( fib(pos) == num)
                     assert( fib1(pos) == num)
                     assert( fibBig(pos) == num)
                     assert( fibI(pos) == num) }
                   }

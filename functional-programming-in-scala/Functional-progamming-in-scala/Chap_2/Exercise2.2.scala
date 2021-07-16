// vim:ts=2:sw=2:sts=2:et:syntax=scala
//
// Functional Programming in Scala - Exercise 2.2
//
//Array         Length    Start         Result
//()            0         loop(-1)      true
//(1)           1         loop(0)       true
//(1,2)         2         loop(1)       ordered(0,1) = true / loop(0) true
//(1,2,3)       3         loop(2)       ordered(1,2) = true / loop(1) / ordered(0,1) = true / loop(0) true
//(1,2,3,4)     4         loop(3)       ordered(2,3) = true / loop(2) / ordered(1,2) = true / loop(1) / ordered(0,1) = true / loop(0) true
//()            0         loop(-1)      true
//(1)           1         loop(0)       true
//(2,1)         2         loop(1)       ordered(0,1) = false 
//(1,2,1)       3         loop(2)       ordered(1,2) = false
//(1,2,3,1)     4         loop(3)       ordered(2,3) = false
//(1,4,3,4)     4         loop(3)       ordered(2,3) = true / loop(2) / ordered(1,2) = false
//(9,2,3,4)     4         loop(3)       ordered(2,3) = true / loop(2) / ordered(1,2) = true / loop(1) / ordered(0,1) = false
import scala.annotation.tailrec

def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean = {
  @tailrec
  def loop(n: Int): Boolean = {
    if (n <=0) true
    else if (ordered(as(n-1),as(n))) loop(n-1)
    else false
    
  }
  loop(as.length-1)	
}



def intAscOrder(a: Int, b: Int): Boolean = 
  if (a <= b) true else false

def intDescOrder(a: Int, b: Int): Boolean = 
  if (a >= b) true else false

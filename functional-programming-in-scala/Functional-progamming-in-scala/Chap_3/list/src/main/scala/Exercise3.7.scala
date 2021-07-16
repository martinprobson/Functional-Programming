/*
* Functional Programming in Scala
*
* Chapter 3 - Functional Data Structures
*
* Exercise 3.7 - Can product implemented using foldRight, immediately halt 
*                the recursion and return 0.0 if it encouters a 0.0?
*
*/


/*
 *  The call pattern of product implemented with non-tail recursive foldRight looks
 *  as follows. 
 *
 *  product(List(1,2,3,4,5,6,7,8,9,10)) =
 *   foldRight(List(1,2,3,4,5,6,7,8,9,10),1.0)(mult) =
 *    mult(1,foldRight(List(2,3,4,5,6,7,8,9,10),1.0(mult)) =
 *     mult(1,mult(2,foldRight(List(3,4,5,6,7,8,9,10),1.0)(mult))) =
 *      mult(1,mult(2,mult(3,foldRight(List(4,5,6,7,8,9,10),1.0)(mult)))) =
 *       mult(1,mult(2,mult(3,mult(4,foldRight(List(5,6,7,8,9,10),1.0)(mult))))) =
 *        mult(1,mult(2,mult(3,mult(4,mult(5,foldRight(List(6,7,8,9,10),1.0)(mult)))))) =
 *         mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,foldRight(7,8,9,10),1.0)(mult)))))) =
 *          mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,foldRight(8,9,10)(mult)))))))) =
 *           mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,foldRight(9,10)(mult))))))))) =
 *            mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,mult(9,foldRight(10)(mult)))))))))) =
 *  Unwind:-
 *             mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,mult(9,mult(10,foldRight(Nil)(mult))))))))))) =
 *            mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,mult(9,mult(10,1.0)(mult)))))))))) =
 *           mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,mult(9,10.0)(mult))))))))) =
 *          mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,90.0)(mult)))))))) =
 *         mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,720.0)(mult))))))) =
 *        mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,5040.0)(mult)))))) =
 *       mult(1,mult(2,mult(3,mult(4,mult(5,30240.0)(mult))))) =
 *      mult(1,mult(2,mult(3,mult(4,151200.0)(mult)))) =
 *     mult(1,mult(2,mult(3,604800.0)(mult))) =
 *    mult(1,mult(2,1814400.0)(mult)) =
 *   mult(1,3628800.0) =
 *  3628800.0
 *
 *  So, if we add a short-circuit case to foldRight, the stack still has to be unwound, but
 *  will not get any deeper at the point the zero is encountered.
 *
 *  product(List(1,2,3,4,5,6,7,8,0,10)) =
 *   foldRight(List(1,2,3,4,5,6,7,8,0,10),1.0)(mult) =
 *    mult(1,foldRight(List(2,3,4,5,6,7,8,0,10),1.0(mult)) =
 *     mult(1,mult(2,foldRight(List(3,4,5,6,7,8,0,10),1.0)(mult))) =
 *      mult(1,mult(2,mult(3,foldRight(List(4,5,6,7,8,0,10),1.0)(mult)))) =
 *       mult(1,mult(2,mult(3,mult(4,foldRight(List(5,6,7,8,0,10),1.0)(mult))))) =
 *        mult(1,mult(2,mult(3,mult(4,mult(5,foldRight(List(6,7,8,0,10),1.0)(mult)))))) =
 *         mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,foldRight(List(7,8,0,10),1.0)(mult))))))) =
 *          mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,foldRight(List(8,0,10)(mult))))))))) =
 *           mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,foldRight(List(0,10)(mult)))))))))) = <== Short circuit
 *            mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,mult(9,0)(mult))))))))))) =
 *  Unwind:-
 *             mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,mult(9,0)(mult))))))))) =
 *            mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,mult(8,0)(mult)))))))) =
 *           mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,mult(7,0)(mult))))))) =
 *          mult(1,mult(2,mult(3,mult(4,mult(5,mult(6,0)(mult)))))) =
 *         mult(1,mult(2,mult(3,mult(4,mult(5,0)(mult))))) =
 *        mult(1,mult(2,mult(3,mult(4,0)(mult)))) =
 *       mult(1,mult(2,mult(3,0)(mult))) =
 *      mult(1,mult(2,0)(mult)) =
 *     mult(1,0)(mult) =
 *  0.0
 *
 *  Compare this to product implemented using a foldLeft that can return immediately it encounters a zero.
 */
package fpinscala.datastructures


import scala.annotation.tailrec

object ProductTest {

  // tail recursive foldLeft
  // with short circuit
  @tailrec
  def foldLeft(l: List[Double], z: Double)(f: (Double,Double) => Double): Double = l match  {
    case Nil => z 
    case Cons(0,tl) => 0.0
    case Cons(h,tl) => foldLeft(tl,f(z,h))(f) 
  }
  //
  //  This is a non tail recursive version of foldRight that we
  //  will use to implement product.
  //  Note the attempted short-circuit 'Cons(0,tl)'
  def foldRight(l: List[Double], seed: Double)(f: (Double,Double) => Double): Double = l match {
    case Nil => seed
    case Cons(0,tl) => 0.0  // short circuit
    case Cons(h,tl) => f(h,foldRight(tl,seed)(f))
  }

  def product(l: List[Double]) = {
    def mult(x: Double, y: Double) = {
      println(s"mult($x,$y) = ${x*y}")
      x*y
    }
    // Define our own mult function 
    // for trace purposes
    foldRight(l,1.0)(mult)
  }

  // Product implemented with foldLeft 
  def productFl(l: List[Double]) = {
    def mult(x: Double, y: Double) = {
      println(s"mult($x,$y) = ${x*y}")
      x*y
    }
    // Define our own mult function 
    // for trace purposes
    foldLeft(l,1.0)(mult)
  }

  def main(args: Array[String]) {
    val l = List(1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,0.0,10.0)
    val r = product(l)
    println(r)
    val l2 = List(1.0,0.0,3.0,4.0,5.0,6.0,7.0,8.0,0.0,10.0)
    val r2 = productFl(l2)
    println(r2)
  }
}


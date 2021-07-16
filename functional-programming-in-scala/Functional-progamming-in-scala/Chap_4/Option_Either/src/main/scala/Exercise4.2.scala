//
// Functional Programming in Scala
// Chapter 3 - Functional Data Structures
//
// Exercise 4.2 - Implement the variance function in terms of flatMap.
//                If the mean of a sequence is m then the variance is the
//                mean of math.pow(x - m,2) for each element x in the 
//                sequence.

package fpinscala.chap4

object Exercise42 {
  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  
  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs).flatMap( m => mean(xs.map( x => math.pow( x - m,2))))
  }

}

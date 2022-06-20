package fpinscala.chap6

trait RNG {
  def nextInt: (Int,RNG)
}

object Rng {
  /*
  * This is our state action.
  * We are defining a type Rand, this
  * type can take a RNG and can generate an
  * A together with a new RNG
  */
  type Rand[+A] = RNG => (A,RNG)

  /*
  * def int: Rand[Int] = (a: RNG) => a.nextInt
  */
  def int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a,rng)

  def map[A,B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a,rng2) = s(rng)
      (f(a),rng2)
    }

  /*
  * Exercise 6.6 - Write an implementation of map2.
  */
  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A,B) => C): Rand[C] =
    rng => {
      val (a,rng2) = ra(rng)
      val (b,rng3) = rb(rng2)
      (f(a,b),rng3)
  }
  /*
  * Exercise 6.6 - re-implement intDouble and doubleInt 
  *                using map2.
  */
  def intDouble: Rand[(Int,Double)] =
    Rng.map2(Exercise61.nonNegativeInt,Exercise62.double)( (i,d) => (i,d) )

  def doubleInt: Rand[(Double,Int)] = 
    Rng.map2(Exercise62.double,Exercise61.nonNegativeInt)( (d,i) => (d,i) )

  /*
  * Exercise 6.7 - Implement sequence for combining a List of transitions
  *                into a single transition. Use it to reimplement the 
  *                ints function.
  *
  * Notes: Types have been inserted into the foldRight function for clarity: -
  *        r - Each member of List[Rand[A]] i.e. a Rand[A]
  *        l - A tuple2 consisting of List[A] and a RNG.
  *
  */
  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = 
    rng => {
      fs.foldRight( (Nil: List[A],rng) )( (r: Rand[A],l: (List[A],RNG)) => {
        val (i,rng2) = r(l._2)
        (i :: l._1,rng2) })
    }

  /*
  *  Exercise 6.7 - Re-implement the int function using sequence.
  */
  def intsSeq(count: Int)(rng: RNG): (List[Int],RNG) = 
    Rng.sequence(List.fill(count)(int))(rng)
//    Rng.sequence(List.fill(count)(Exercise61.nonNegativeInt(_)))(rng)


  /*
  *  Exercise 6.8 - Implement flatMap, and then use it to implement
  *                 nonNegativeLessThan.
  */
  def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] =
    rng => {
      val (a,rng2) = f(rng)
      g(a)(rng2)
    }

  def nonNegativeLessThan(n: Int): Rand[Int] = 
    flatMap(Exercise61.nonNegativeInt) { i =>
      val mod = i % n
      if (i + (n-1) - mod >= 0) rng => (mod,rng)
      else nonNegativeLessThan(n)
    }

  /*
  *  Exercise 6.9 - Implement map in terms of flatMap
  */
  def map_FM[A,B](s: Rand[A])(f: A => B): Rand[B] =
    flatMap(s){ i => rng => (f(i),rng)}

  /*
  *  Exercise 6.9 - Implement map2 in terms of flatMap
  */
  def map2_FM[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A,B) => C): Rand[C] =
    flatMap(ra){ a => rng => { val (b,rng2) = rb(rng)
                               (f(a,b),rng2) } }

  /*
  * Exercise 6.9 - re-implement intDouble and doubleInt 
  *                using map2_FM.
  */
  def intDouble_FM: Rand[(Int,Double)] =
    Rng.map2_FM(Exercise61.nonNegativeInt,Exercise62.double)( (i,d) => (i,d) )

  def doubleInt_FM: Rand[(Double,Int)] = 
    Rng.map2_FM(Exercise62.double,Exercise61.nonNegativeInt)( (d,i) => (d,i) )


}


/**
* Let's use the map above to generate a negativeInt using nonNegativeInt
*/
import Exercise61.nonNegativeInt
import Exercise62.double
import Rng._
 
object NegativeInt {
  def negativeInt: Rand[Int]  =
    Rng.map(nonNegativeInt)( i => i * -1)
 
  def nonNegativeEven: Rand[Int] =
    Rng.map(nonNegativeInt)(i => i - i % 2)
}
 
object Tester {
  def intDouble: Rand[(Int,Double)] =
    Rng.map2(nonNegativeInt,double)( (i,d) => (i,d) )
 
 
}

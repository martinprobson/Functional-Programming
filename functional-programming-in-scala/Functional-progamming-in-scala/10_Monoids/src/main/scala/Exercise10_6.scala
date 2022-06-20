
import Exercise10_5._

//
// Exercise 10.6
// Define foldLeft in terms of foldMap
//
object Exercise10_6 {

  def foldLeft[A](as: List[A], z: A)(f: (A,A) => A): A = {
    val m = new Monoid[A] {
      def op(a1: A,a2: A): A = f(a1,a2)
      def zero: A = z
    }
    foldMap[A,A](as,m)( (a: A) => a)
  }
}

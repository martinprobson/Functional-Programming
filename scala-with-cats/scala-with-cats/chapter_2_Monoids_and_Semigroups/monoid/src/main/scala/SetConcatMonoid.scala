
class SetConcatMonoid[A] extends Monoid[Set[A]] {
  def combine(x: Set[A], y: Set[A]) = x ++ y
   
  def empty = Set[A]()
}

object SetConcatMonoid extends App {
  val m = MonoidInstances(new SetConcatMonoid[Int])
  val r = m.combine(Set(1,2,3),Set(4,5,6))
  println(r)
}



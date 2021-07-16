
class ListConcatMonoid[A] extends Monoid[List[A]] {
  def combine(x: List[A], y: List[A]) = List.append(x,y)
   
  def empty = List[A]()
}


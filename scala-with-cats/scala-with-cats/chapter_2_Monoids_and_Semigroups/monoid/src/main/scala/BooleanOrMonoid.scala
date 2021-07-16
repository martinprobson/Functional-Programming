
object BooleanOrMonoid extends Monoid[Boolean] {
  def combine(x: Boolean, y: Boolean) = x || y
   
  def empty = false
}



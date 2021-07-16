

object BooleanAndMonoid extends Monoid[Boolean] {
  def combine(x: Boolean, y: Boolean) = x && y
   
  def empty = true
}



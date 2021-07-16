object IntAdditionMonoid extends Monoid[Int] {
  def combine(x: Int, y: Int) = x + y
  def empty = 0
}

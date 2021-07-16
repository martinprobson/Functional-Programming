object StringConcatMonoid extends Monoid[String] {
  def combine(x: String, y: String) = x ++ y
  def empty = ""
}

class OptionMonoid[A](implicit A: Monoid[A]) extends Monoid[Option[A]] {
  def empty: Option[A] = None
  def combine(x: Option[A], y: Option[A]): Option[A]  =
    x match {
      case None => y
      case Some(a) =>
        y match {
          case None => x
          case Some(b) => Some(A.combine(a,b))
        }
    }
}

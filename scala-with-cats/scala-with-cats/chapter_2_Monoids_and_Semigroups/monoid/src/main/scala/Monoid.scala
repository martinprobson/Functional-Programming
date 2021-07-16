trait Monoid[A] extends SemiGroup[A] {
  def empty: A
}

object MonoidInstances {
  def apply[A](implicit monoid: Monoid[A]) = monoid

  implicit val intAdditionMonoid: Monoid[Int] = IntAdditionMonoid
  implicit val intMultiplyMonoid: Monoid[Int] = IntMultiplicationMonoid
  implicit val stringConcatMonoid: Monoid[String] = StringConcatMonoid
  implicit val booleanOrMonoid: Monoid[Boolean] = BooleanOrMonoid
  implicit def optionMonoid[A: Monoid]: Monoid[Option[A]] = new OptionMonoid[A]
}



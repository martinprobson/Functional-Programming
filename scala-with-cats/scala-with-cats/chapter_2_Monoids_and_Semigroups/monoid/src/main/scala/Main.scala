import MonoidInstances.intAdditionMonoid

object Main extends App {

  def add2[A](items: List[A])(implicit m: Monoid[A]): A = {
    List.foldLeft(items,m.empty)(m.combine(_,_))
  }

  def add(items: List[Int]): Int = {
    val m = MonoidInstances(IntAdditionMonoid)
    List.foldLeft(items,m.empty)(m.combine(_,_))
  }

  println(add2(List(1,2,3,4,5,6,7,8,9,10)))
  import MonoidInstances.stringConcatMonoid
  println(add2(List("a","b")))
  import MonoidInstances.booleanOrMonoid
  println(add2(List(true,false,true)))
  import MonoidInstances.optionMonoid
  println(add2(List(Some(1): Option[Int],Some(2))))
  println(add2(List(Some("1"): Option[String],Some("2"))))

}

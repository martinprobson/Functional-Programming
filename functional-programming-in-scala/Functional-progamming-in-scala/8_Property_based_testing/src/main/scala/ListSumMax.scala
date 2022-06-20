
package fpinscala.chapter8


object Main extends App {

  def sum(l: List[Int]): Int = 
    l.foldLeft(0){_ + _}

  def max(l: List[Int]) = l.foldLeft(Int.MinValue){ math.max(_,_) }

  val a: Option[String]  = Some("Martin")
  def f1(i: Int) = i + 1
  def f2(i: Int) = i * 10
  val p = f1 _ compose f2
}

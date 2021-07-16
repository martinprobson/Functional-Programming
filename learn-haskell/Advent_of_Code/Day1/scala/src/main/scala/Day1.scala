import scala.annotation.tailrec

object Day1 extends App {
  val s = scala.io.Source.fromFile(args(0)).getLines.toSeq.map(_.toLong)
  val r = pair(s).filter(_.sum == 2020).map(_.product).head
  println(r)

  def _pair(x: Long,xs: Seq[Long]): Seq[Seq[Long]] = {
    @tailrec
    def _pair_tailrec(x: Long, xs: Seq[Long], acc: Seq[Seq[Long]]): Seq[Seq[Long]] = {
      (x, xs) match {
        case (_, Nil) => Nil
        case (n, x :: Nil) => acc ++ Seq(Seq(n, x))
        case (n, x :: xs) => _pair_tailrec(n,xs,acc ++ Seq(Seq(n,x)))
      }

    }
    _pair_tailrec(x, xs, Seq())
  }

  def pair(a: Seq[Long]): Seq[Seq[Long]] = {
    @tailrec
    def pair_tailrec(a: Seq[Long], acc: Seq[Seq[Long]]): Seq[Seq[Long]] = {
      a match {
        case Nil => acc
        case x :: xs => pair_tailrec(xs, acc ++ _pair(x, xs))
      }
    }
    pair_tailrec(a, Seq())
  }
}

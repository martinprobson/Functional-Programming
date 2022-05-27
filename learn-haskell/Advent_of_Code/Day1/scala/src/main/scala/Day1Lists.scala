import common.{Day1, Logging}

class Day1Lists extends Day1 with Logging {

  override def day1(s: Seq[Int]): Int = pair(s.toList).filter(_.sum == 2020).map(_.product).head

  override def day1_part2(s: Seq[Int]): Int =
    threes(s.toList).filter(_.sum == 2020).map(_.product).head

  private def threes(s: List[Int]): List[List[Int]] = s match {
    case List() => List()
    case x :: xs => {
      val res = pair(xs)
        //FIXME Build up a list of pairs and then filtering, instead do not build list!!
        //      Use a fold?
        .map(s => {
          x +: s
        })
        .filter(_.sum == 2020)
      if (res.nonEmpty)
        res
      else
        threes(xs)
    }
  }

  private def pair(a: List[Int]): List[List[Int]] = {

    def _pair(a: Int, as: List[Int]): List[List[Int]] = (a, as) match {
      case (_, List())  => List()
      case (n, x :: xs) => List(List(n, x)) ++ _pair(n, xs)
    }

    a match {
      case List()  => List()
      case x :: xs => _pair(x, xs) ++ pair(xs)
    }
  }
}

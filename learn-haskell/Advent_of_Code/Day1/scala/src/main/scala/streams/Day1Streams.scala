package streams

import common.{Day1, Logging}

class Day1Streams extends Day1 with Logging {

  override def day1(s: Seq[Int]): Int = pair(s.to(LazyList)).filter(_.sum == 2020).map(_.product).head

  override def day1_part2(s: Seq[Int]): Int =
    threes(s.to(LazyList)).filter(_.sum == 2020).map(_.product).head

  private def threes(s: LazyList[Int]): LazyList[LazyList[Int]] = s match {
    case LazyList() => LazyList()
    case LazyList(x,xs*) => {
      val res = pair(xs.to(LazyList))
        //FIXME Build up a list of pairs and then filtering, instead do not build list!!
        //      Use a fold?
        .map(s => {
          x +: s
        })
        .filter(_.sum == 2020)
      if (res.nonEmpty)
        res
      else
        threes(xs.to(LazyList))
    }
  }

  private def pair(a: LazyList[Int]): LazyList[LazyList[Int]] = {

    def _pair(a: Int, as: LazyList[Int]): LazyList[LazyList[Int]] = (a, as) match {
      case (_, LazyList())  => LazyList()
      case (n, LazyList(x,xs*)) => LazyList(LazyList(n, x)) ++ _pair(n, xs.to(LazyList))
    }

    a match {
      case LazyList()  => LazyList()
      case LazyList(x,xs*) => _pair(x, xs.to(LazyList)) ++ pair(xs.to(LazyList))
    }
  }
}

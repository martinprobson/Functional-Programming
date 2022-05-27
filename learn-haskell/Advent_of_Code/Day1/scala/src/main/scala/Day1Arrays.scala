import common.{Day1, Logging}

class Day1Arrays extends Day1 with Logging {

  override def day1(s: Seq[Int]): Int = pair(s.toArray).filter(_.sum == 2020).map(_.product).head

  override def day1_part2(s: Seq[Int]): Int = threes(s.toArray).filter(_.sum == 2020).map(_.product).head

  private def threes(s: Array[Int]): Array[Array[Int]] = s match {
    case Array() => Array()
    case Array(x, xs*) => {
      val res = pair(xs.toArray)
        .map(s => {
          x +: s
        }).filter(_.sum == 2020)
      if (res.length > 0)
        res
      else
        threes(xs.toArray)
    }
  }

  private def pair(a: Array[Int]): Array[Array[Int]] = {

    def _pair(a: Int, as: Array[Int]): Array[Array[Int]] = (a, as) match {
      case (_, Array())           => Array()
      case (n, Array(x, xs*)) => Array(Array(n, x)) ++ _pair(n, xs.toArray)
    }

    a match {
      case Array()           => Array()
      case Array(x, xs*) => _pair(x, xs.toArray) ++ pair(xs.toArray)
    }
  }
}

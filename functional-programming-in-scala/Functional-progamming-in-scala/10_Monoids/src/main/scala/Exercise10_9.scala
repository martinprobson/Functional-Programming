//
// Exercise10.9 - Use foldMap to detect whether a given IndexSeq[Int] is ordered.
//                You'll need to come up with a creative Monoid.
//
object Exercise10_9 extends Logging {

  def sequenceMonoid: Monoid[(Int, Option[Boolean])] =
    new Monoid[(Int, Option[Boolean])] {
      def op(
          a1: (Int, Option[Boolean]),
          a2: (Int, Option[Boolean])
      ): (Int, Option[Boolean]) = {
        val res: (Int, Option[Boolean]) = if (a1._1 <= a2._1) {
          (a1._1, Some(a2._2.getOrElse(true)))
        } else {
          (a1._1, Some(false))
        }
        logger.debug(s"op($a1,$a2) = $res")
        res
      }
      def zero: (Int, Option[Boolean]) = (-9990, None)
    }
}

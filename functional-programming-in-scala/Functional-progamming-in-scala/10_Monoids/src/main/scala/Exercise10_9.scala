//
// Exercise 10.9
// Use foldMap to determine whetner a given IndexedSeq[Int] is ordered.
//
import Exercise10_7.*

object Exercise10_9 extends Logging with App {

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

//  logger.info(
//    s" ans = ${foldMapV(IndexedSeq(0, -1, 0, 0), testMonoid)(i => (i, None))}"
//  )

}

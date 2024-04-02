//
// Exercise10.10 - Write a Monoid instance for WC and make sure it meets the Monoid laws.
//
object Exercise10_10 extends Logging {

  def WCMonoid: Monoid[WC] =
    new Monoid[WC] {
      def op(a1: WC, a2: WC): WC = (a1, a2) match {
        case (Stub(a), Stub(b)) => Stub(a + b)
        case (Stub(a), Part(l, w, r)) => Part(a + l, w, r)
        case (Part(l, w, r), Stub(b)) => Part(l, w, r + b)
        case (Part(l1, w1, r1), Part(l2, w2, r2)) => Part(l1, w1 + (if ((r1 + l2).isEmpty) 0 else 1) + w2, r2)
      }
      def zero: WC = Stub("")
    }
}

//
// Exercise 10.7
// Implement a foldMap for IndexedSeq
//
object Exercise10_7 {

  def foldMapV[A,B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = v match {
      case IndexedSeq() => m.zero
      case a +: IndexedSeq() => f(a)
      case _ => {
        val (l,r) = v.splitAt(v.length/2)
        m.op(foldMapV(l,m)(f),foldMapV(r,m)(f))
      }
  }
}

//
// Exercise 10.8
// Implement par, a combinator to promote Monoid[A] to Monoid[Par[A]].
//
//
import Par.*

object Exercise10_8 {

  def par[A](m: Monoid[A]): Monoid[Par[A]] = new Monoid[Par[A]] {
    def op(a1: Par[A], a2: Par[A]): Par[A] = {
      map2(a1, a2)((a: A, b: A) => m.op(a, b))
    }

    def zero: Par[A] = lazyUnit(m.zero)
  }

  def parFoldMap[A, B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): Par[B] =
    v match {
      case IndexedSeq()      => unit(m.zero)
      case a +: IndexedSeq() => unit(f(a))
      case _ => {
        val (l, r) = v.splitAt(v.length / 2)
        // par(m).op(parFoldMap(l, par(m))(f), parFoldMap(r, par(m))(f))
        fork(par(m).op(parFoldMap(l, m)(f), parFoldMap(r, m)(f)))
      }
    }
}

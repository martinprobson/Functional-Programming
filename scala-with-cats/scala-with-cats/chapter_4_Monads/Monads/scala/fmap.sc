/**
 * Scala typeclass example (with high kinded type).
 *
 */
//
// This is our typeclass
//
trait Mapper[F[_]] {
  def fmap[A,B](fa: F[A] )(f: A => B): F[B]
}

//
// Instances of our typeclass
//
object MapInstances {
  // Option
  implicit val optionMapper: Mapper[Option] = new Mapper[Option] {
    def fmap[A,B](fa: Option[A])(f: A => B): Option[B] = fa match {
      case None => None
      case Some(a) => Some(f(a))
    }
  }

  // List
  // Use Scala's built in map for lists.
  implicit val listMapper: Mapper[List] = new Mapper[List] {
    def fmap[A,B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }
}

//
// Our typeclass interface
//
object MapLike {
  def fmap[F[_],A,B](fa: F[A])(f: A => B)(implicit m: Mapper[F]): F[B] = m.fmap(fa)(f)
}

object MapSyntax {
  implicit class MapOps[F[_],A,B](fa: F[A]) {
    def fmap(f: A => B)(implicit m: Mapper[F]): F[B] = m.fmap(fa)(f)
  }
}

//
// Example usage
//
import MapInstances._
import MapLike._
import MapSyntax._

import scala.language.higherKinds
// Notice that we need the specific type annotation of `Option[Int]` here. This is because
// the type A in Mapper is NOT marked as covariant (+A).
val res1 = fmap(Some(10): Option[Int])(i => i + 2)
println(s"res1 = $res1")
val res2 = fmap(List(1,2,3,4))(i => i + 2)
println(s"res2 = $res2")
// Using syntax
//
val res3 = (Some(10): Option[Int]).fmap(_+2)
println(s"res3 = $res3")
val res4 = List(1,2,3,4).fmap(_+2)
println(s"res4 = $res4")

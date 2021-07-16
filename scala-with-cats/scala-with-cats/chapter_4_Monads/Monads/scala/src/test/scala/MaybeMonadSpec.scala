import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import ScalaCheckImplicits._
import Monad._

object MonadSpec extends Properties ("MaybeMonad obeys the Monad laws") {

//  def rightIdentity[F[_]: Monad](a: F[Int])(implicit m: Monad[F]): Boolean = {
//    m.flatMap(a)(m.pure) == a
//  }
    def rightIdentity[F[_]](a: F[_])(implicit m: Monad[F]): Boolean = {
      m.flatMap(a)(m.pure) == a
    }

  property("right identity - String") = forAll { (i: MyList[String]) => rightIdentity(i)}
  property("right identity - Int") = forAll { (i: MyList[Int]) => rightIdentity(i)}

}

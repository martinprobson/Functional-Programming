
def tuple[A,B](as: List[A], ab: List[B]): List[(A,B)] =
    as.flatMap( a => ab.map( b => (a,b)))

def tuple2[F[_] : Monad,A,B](as: F[A], ab: F[B])(implicit m: Monad[F]): F[(A,B)] = {
    m.flatMap(as)(a => m.map(ab)((a,_)))
}

def double2[F[_] : Monad](as: F[Int])(implicit m: Monad[F]): F[Int] =
    m.map(as)(_*2)

tuple2(MyList(1,2,3),MyList("A","B","C"))
tuple2(Some(1) : Option[Int],Some(2): Option[Int])
tuple2(Return(1): Maybe[Int], Return(2): Maybe[Int])
tuple2(Return(1): Maybe[Int], Raise("Error"): Maybe[Int])

double2(MyList(1,2,3))
double2(Return(2): Maybe[Int])
double2(Some(2): Option[Int])
import tree.{Branch,Leaf,Tree}
double2(Branch(Leaf(2),Leaf(3)): Tree[Int])

import org.scalacheck.Gen
val intList = Gen.listOf(Gen.choose(0,100))
intList.sample

intList.sample

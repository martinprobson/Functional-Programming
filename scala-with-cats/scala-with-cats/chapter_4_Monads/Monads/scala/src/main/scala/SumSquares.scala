import Monad._
import tree.Tree.insert
import tree._

object SumSquares extends App {

  def SumSquares[F[_]: Monad](a: F[Int], b: F[Int])(implicit m: Monad[F]) =
    m.flatMap(a)(x => m.map(b)(y => x*x + y*y))

  val r = SumSquares(MyList(2,3,4),MyList(2,3,4))
  println(r)
  val s: Option[Int] = Some(2)
  val r2 = SumSquares(s,s)
  println(r2)

  val tree1 = Seq(3,4).foldLeft(Leaf(2): Tree[Int]){
    (t,i) => insert(i,t)
  }
  val tree2 = Seq(3,4).foldLeft(Leaf(2): Tree[Int]){
    (t,i) => insert(i,t)
  }
  val r3 = SumSquares(tree1,tree2)
  println(r3)


//  val r3 = SumSquares(2: Id[Int],2: Id[Int])
//  println(r3)
//  val x: Monad[Id] = idMonad.pure(2)
//  println(x)
//  val y = idMonad.map(x)(_*2)
//  println(y)
}



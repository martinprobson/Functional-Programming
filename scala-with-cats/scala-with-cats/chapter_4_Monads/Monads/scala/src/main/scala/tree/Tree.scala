package tree

import cats.Functor
import cats.Eq
import cats.implicits._

sealed trait Tree[+A]


case class Branch[+A](left: Tree[A], right: Tree[A]) extends Tree[A]

case class Leaf[A](value: A) extends Tree[A]

object Tree {
  implicit def treeEq[A]: Eq[Tree[A]] = {
    def _equal(t1: Tree[A], t2: Tree[A]): Boolean = (t1, t2) match {
      case (Leaf(x), Leaf(y)) => x == y
      case (Branch(l1, r1), Branch(l2, r2)) => _equal(l1, l2) && _equal(r1, r2)
      case _ => false
    }

    Eq.instance[Tree[A]] { (t1, t2) => _equal(t1, t2) }
  }

  implicit val treeFunctor: Functor[Tree] =
    new Functor[Tree] {
      def map[A,B](t: Tree[A])(f: A => B): Tree[B] = t match {
        case Leaf(v) => Leaf(f(v))
        case Branch(l,r) => Branch(map(l)(f),map(r)(f))
      }
    }

  def insert[A](v: A, t: Tree[A])(implicit o: Ordering[A]): Tree[A] = t match {
    case Leaf(l) => if (o.lteq(v,l)) Branch(Leaf(v),Leaf(l)) else Branch(Leaf(l),Leaf(v))
    case Branch(l,r) => if (o.lteq(v,maximum(l))) Branch(insert(v,l),r)
                        else Branch(l,insert(v,r))
  }

  def maximum[A](t: Tree[A])(implicit o: Ordering[A]): A = t match {
    case Leaf(i) => i
    case Branch(left,right) => o.max(maximum(left), maximum(right))
  }
}

import Tree._
object TreeApp extends App {

  val l1: Tree[Int] = Branch(Leaf(1),Leaf(2))
  println(l1 === l1)
  val tree = Seq(11,10,20,12,5).foldLeft(l1){
    (t,i) => insert(i,t)
  }
  println(tree.toString)
  println(maximum(tree))
  println("Tree === Tree")
  println(tree === tree)
  val tree2 = Seq("foo","hj","yuyu","martin").foldLeft(Leaf("z"): Tree[String]){
    (t,i) => insert(i,t)
  }
  println(tree2.toString)
  println(maximum(tree2))
  val tree3 = Seq(10,2,12,50).foldLeft(Leaf(1): Tree[Int]){
    (t,i) => insert(i,t)
  }
  println(tree3 === tree)
 // println(Nil === Nil)
}


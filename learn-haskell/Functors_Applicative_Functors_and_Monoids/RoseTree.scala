//
// Define and instance of Functor for the following type
//
sealed trait Tree[+A]

case class Node[A](a: A, r: List[Tree[A]]) extends Tree[A]

object RoseTree {
  def _new[A](a: A): Tree[A] = Node(a, List(): List[Tree[A]])

  // Build a new (single node tree)
  // Note: This is not tail recursive and should be
  def ins[A](a: A)(t : Tree[A]): Tree[A] = (a, t) match {
    case (a, Node(b, List())) => Node(b, List(Node(a, List())))
    case (a, Node(b, List(c))) => Node(b, List(ins(a)(c)))
  }

  //
  // Build a sample tree
  //
  def buildTree: Tree[Int] = (1 to 100)
    .toList
    .foldLeft(_new(0): Tree[Int])( (t: Tree[Int], i: Int) => ins(i)(t))

  def map[A,B](t: Tree[A])(f: A => B): Tree[B] = (t,f) match {
    case (Node(b, List()), f) => Node(f(b), List())
    case (Node(b, c), f) => Node(f(b), c.map( (t: Tree[A]) => RoseTree.map(t)(f)))

  }
}

//
// Main
//
RoseTree.map(RoseTree.buildTree)(_*1000)




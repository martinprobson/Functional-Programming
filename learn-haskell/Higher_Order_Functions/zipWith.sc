def zipWith[A,B,C](f: (A,B) => C)(l1: List[A])(l2: List[B]): List[C] = (f,l1,l2) match {
  case (_,Nil,_) => Nil
  case (_,_,Nil) => Nil 
  case (f,(x :: xs),(y :: ys)) => f(x,y) :: zipWith(f)(xs)(ys)
}

//scala> zipWith(zipWith(add)(_: List[Int])(_: List[Int]))(List(List(1),List(2),List(3)))(List(List(1),List(2),List(3)))
//res10: List[List[Int]] = List(List(2), List(4), List(6))

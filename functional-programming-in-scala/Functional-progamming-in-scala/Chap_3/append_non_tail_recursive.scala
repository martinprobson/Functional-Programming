
import scala.annotation.tailrec

def append[A](xs: List[A], ys: List[A]): List[A] = 
  xs match {
    case List() => ys
    case x :: xs1 =>  x :: append(xs1,ys)
}

//append(List(1,2,3),List(4,5,6))
//1 :: append(List(2,3),List(4,5,6))
//1 :: append(2 :: append(List(3),List(4,5,6)))
//1 :: append(2 :: 3 :: 4 :: 5 :: 6)


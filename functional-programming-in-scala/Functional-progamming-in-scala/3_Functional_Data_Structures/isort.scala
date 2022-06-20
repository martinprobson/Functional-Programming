import scala.annotation.tailrec

//
// Will NOT compile - not tail recursive
//@tailrec
def insert(x: Int, xs: List[Int]): List[Int] = {
  if (xs.isEmpty || x <= xs.head) x :: xs
  else xs.head :: insert(x,xs.tail)
}

//@tailrec
def isort(xs: List[Int]): List[Int] = {
  if (xs.isEmpty) Nil
  else insert(xs.head, isort(xs.tail))
}


// isort(List(3,2,1))
//  insert(3,isort(List(2,1)))
//    isort(List(2,1))
//      insert(2,isort(List(1)))
//        isort(List(1))
//          insert(1,isort(List()))
//            isort(List()) 
//              Nil

// isort(List(3,2,1))
// isort(insert(3,isort(List(2,1))))
// isort(insert(3,insert(2,isort(List(1)))))
// isort(insert(3,insert(2,insert(1,isort(Nil)))))
// unwind: -
// isort(insert(3,insert(2,insert(1,Nil))))
// isort(insert(3,insert(2,List(1))))
// isort(insert(3, 1 :: insert(2,Nil)))
// isort(insert(3, List(1,2)))
// isort(3, List(1,2)))
//  
def insertionSort(xs: List[Int]): List[Int] = {
  @tailrec
  def insert(xs: List[Int], x: Int): List[Int] = {
    val (h,t) = xs.span( _ < x)
    h ::: (x :: t)
  }
  xs.foldLeft(List[Int]()) { (acc,a) => insert(acc,a) }
}

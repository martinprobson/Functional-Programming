import scala.annotation.tailrec


//@tailrec
// non-tail recursive implementation
//
def reverse1(l: List[Int]): List[Int] = {
  if (l.isEmpty) List[Int]()
  else reverse1(l.tail) ::: List[Int](l.head)
}

//
// Tail recursive implementation
// (using helper function)
def reverse2[A](l: List[A]): List[A] = {
  @tailrec
  def _reverse(res: List[A], rem: List[A]): List[A] = rem match {
    case Nil => res
    case h :: tail => _reverse(h :: res,tail)
  }
  _reverse(Nil,l)
}

// Scala collections library implementation
//
def reverse3[A](l: List[A]): List[A] = {
  var result: List[A] = Nil
  var these = l
  while (!these.isEmpty) {
    result = these.head :: result
    these = these.tail
  }
  result
}

// 
// Elegant solution using foldLeft and pattern matching
//
def reverse4[A](l: List[A]): List[A] = {
  l.foldLeft(List[A]()) { (r,h) => h :: r }
}


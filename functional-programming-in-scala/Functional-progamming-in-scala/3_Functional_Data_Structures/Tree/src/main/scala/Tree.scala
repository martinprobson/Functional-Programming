package fpinscala.datastructures

//
// Simple binary tree
//

sealed trait Tree[+A]

case object Nil extends Tree[Nothing]

case class Leaf[A](value: A) extends Tree[A]

case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]



object Tree {

  //
  // Exercise 3.25 - Write a function size that counts the number
  //                 of nodes (leaves and branches) in the tree.
  //
  def size[A](t: Tree[A]): Int = t match {
    case Nil => 0
    case Leaf(_) => 1
    case Branch(left,right) => 1 + size(left) + size(right)
  }
    
  //
  // Exercise 3.26 - Write a function maximum that returns the maximum
  //                 element in a tree.
  //
  def maximum(t: Tree[Int]): Int = t match {
    case Nil => throw new java.lang.UnsupportedOperationException("max of an empty tree")
    case Leaf(i) => i
    case Branch(left,right) => maximum(left).max(maximum(right))
  }

  // 
  // Exercise 3.28 - Write a function map, analogous to the map
  //                 method on List.
  //
  def map[A,B](t: Tree[A])(f: A => B): Tree[B] = t match {
    case Nil => Nil
    case Branch(l,r) => Branch(map(l)(f),map(r)(f))
    case Leaf(i) => Leaf(f(i))
  }

  // 
  // Exercise 3.27 - Write a function depth that returns 
  //                 the maximum path length from the root
  //                 of a tree to any leaf.
  //
  def depth[A](t: Tree[A]): Int = {
    def _depth[A](t: Tree[A],n: Int): Int = t match {
      case Nil => 0
      case Leaf(_) => n + 1
      case Branch(l,r) => _depth(l,n + 1) max _depth(r, n + 1)
    }
    _depth(t,0)
  }


  // 
  // Exercise 3.29 - Generalize size,maximum, depth an map, writing a 
  //                 new function fold that abstracts over their similarities.
  //                 re-implement them in terms of this more general function.
  //
  def fold[A,B](t: Tree[A], seed: B)(lf: (A,B) => B)(bf: (B,B) => B): B =  t match {
    case Nil => seed
    case Leaf(a) => lf(a,seed)
    case Branch(l,r) => bf(fold(l,seed)(lf)(bf),fold(r,seed)(lf)(bf))
  }

  //
  // Exercise 3.29 - map implemented using fold 
  //
  def foldMap[A,B](t: Tree[A])(f: A => B): Tree[B] = {
    fold(t,Nil:Tree[B]){ (a,b) => Leaf(f(a)) }{ (b1,b2) => Branch(b1,b2) }  
  }

  //
  // Exercise 3.29 - size implemented via fold
  //
  def foldSize[A](t: Tree[A]): Int = 
    fold(t,0){ (a,b) => b+1}{ (b1,b2) => b1+b2+1}

  //
  // Exercise 3.29 - depth implemented via fold
  //
  def foldDepth[A](t: Tree[A]): Int =
    fold(t,0){ (a,b) => b+1 }{ (b1,b2) => (b1+1).max(b2+1)  }

  //
  // Exercise 3.29 - maximum implemented via fold
  //
  def foldMaximum(t: Tree[Int]): Int = {
    if (foldSize(t) == 0) throw new java.lang.UnsupportedOperationException("max of an empty tree")
    else fold(t,0){ (a,b) => a }{ (b1,b2) => b1 max b2 }
  }

}

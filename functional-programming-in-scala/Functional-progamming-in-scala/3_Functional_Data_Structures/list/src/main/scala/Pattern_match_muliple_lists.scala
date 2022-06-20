
/*
* Functional Programming in Scala
*
* Test patten match on tuple2 of Lists
*
*/

package fpinscala.datastructures


import scala.annotation.tailrec

object PatternTest {

  def testMatch( l1: List[Int], l2: List[Int]): List[Int]  = (l1, l2) match {
      case (Nil,Nil) => Nil
      case (Nil,l2)  => l2
      case (l1,Nil)  => l1
      case (Cons(h1,Nil),Cons(h2,t2)) => Cons(h1 + h2,t2)
      case (Cons(h1,t1),Cons(h2,Nil)) => Cons(h1 + h2,t1)
      case (Cons(h1,t1),Cons(h2,t2)) => Cons(h1 + h2,testMatch(t1,t2))
      case _ => List(999999)
    }

  def main(args: Array[String]) {
    val tc = List( (Nil,Nil),
                   (Nil,List(1,2,3)),
                   (List(1,2,3),Nil),
                   (List(10),List(1,2,3)),
                   (List(10),List(1)),
                   (List(1,2,3),List(10)),
                   (List(1),List(10)),
                   (List(1,2),List(3,4)),
                   (List(1,2),List(3,4,5)),
                   (List(3,4,5),List(1,2))
                 )
    
    List.foreach(tc){ case (l1,l2) => println(s"l1 = $l1 l2 = $l2 RETURNS: " + testMatch(l1,l2)) }
  }
}


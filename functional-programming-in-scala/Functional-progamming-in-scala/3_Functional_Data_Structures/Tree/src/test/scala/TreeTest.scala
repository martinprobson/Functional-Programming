
package fpinscala.datastructures.test

import fpinscala.datastructures._
import org.scalatest.FunSuite

class TreeTest extends FunSuite {

  val t1 = Branch(
            Branch(
              Branch(
                Leaf(1),
                Leaf(3)
              ),
              Branch(
                Leaf(100),
                Leaf(45)
              )),
            Branch(
              Branch(
                Leaf(90),
                Leaf(99)
              ),
              Branch(
                Leaf(35),
                Leaf(78)
              )))
  val t2 = buildTestTree(Range(1,101).toList)
  val t3 = Branch(
             Branch(
               Branch(
                 Leaf(3),
                 Leaf(4)
               ), 
               Leaf(10)
             ),
             Branch(
               Leaf(34),
               Leaf(90)
             )
           )
  //
  // for testing only
  //
  def buildTestTree(s: Seq[Int]): Tree[Int] = {
    def _insert(t: Tree[Int], v: Int): Tree[Int] = t match {
      case Nil => Leaf(v)
      case Leaf(i) => Branch(t,Leaf(v))
      case Branch(_,_) => Branch(t,Leaf(v))
    }
   var t:Tree[Int] = Nil
   s.sorted.foreach( i => t = _insert(t,i))  
   t
 }

 test("Tree: test size") {
   assert(Tree.size(Nil) === 0)
   assert(Tree.size(t1) === 15)
   assert(Tree.size(t2) === 199)
 }

 test("Tree: test foldSize") {
   assert(Tree.foldSize(Nil) === 0)
   assert(Tree.foldSize(t1) === 15)
   assert(Tree.foldSize(t2) === 199)
 }

 test("Tree: test depth") {
   assert(Tree.depth(Nil) === 0)
   assert(Tree.depth(t1) === 4)
   assert(Tree.depth(t2) === 100)
 }

 test("Tree: test foldDepth") {
   assert(Tree.foldDepth(Nil) === 0)
   assert(Tree.foldDepth(t1) === 4)
   assert(Tree.foldDepth(t2) === 100)
 }

 test("Tree: test maximum") {
    assertThrows[UnsupportedOperationException] {
      Tree.maximum(Nil)
    }
   assert(Tree.maximum(t1) === 100)
   assert(Tree.maximum(t2) === 100)
 }

 test("Tree: test foldMaximum") {
    assertThrows[UnsupportedOperationException] {
      Tree.foldMaximum(Nil)
    }
   assert(Tree.foldMaximum(t1) === 100)
   assert(Tree.foldMaximum(t2) === 100)
 }

 test("Tree: test map") {
    val exp1 = Branch(
              Branch(
                Branch(
                  Leaf(2),
                  Leaf(4)
                ),
                Branch(
                  Leaf(101),
                  Leaf(46)
                )),
              Branch(
                Branch(
                  Leaf(91),
                  Leaf(100)
                ),
                Branch(
                  Leaf(36),
                  Leaf(79)
                )))
   val exp2 = buildTestTree(Range(2,102).toList)
   assert(Tree.map(t1){_+1}  === exp1)
   assert(Tree.map(t2){_+1} === exp2)
 }

 test("Tree: test foldMap") {
    val exp1 = Branch(
              Branch(
                Branch(
                  Leaf(2),
                  Leaf(4)
                ),
                Branch(
                  Leaf(101),
                  Leaf(46)
                )),
              Branch(
                Branch(
                  Leaf(91),
                  Leaf(100)
                ),
                Branch(
                  Leaf(36),
                  Leaf(79)
                )))
   val exp2 = buildTestTree(Range(2,102).toList)
   assert(Tree.foldMap(t1){_+1}  === exp1)
   assert(Tree.foldMap(t2){_+1} === exp2)
   assert(Tree.foldMap(t1){_+1}  === Tree.map(t1){_+1})
   assert(Tree.foldMap(t2){_+1} === Tree.map(t2){_+1})
 }

}

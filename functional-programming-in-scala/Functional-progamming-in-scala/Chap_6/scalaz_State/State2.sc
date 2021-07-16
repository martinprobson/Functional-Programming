import scalaz._
import Scalaz._
import java.util.Random

def dice(): State[Random,Int] =
  State( r => (r,r.nextInt(6) + 1))

//
// run  -  Run and return the final value and state
// exec -  Run and return the final state
// eval -  Run and return the final value

dice()(new Random(10))

def threeDice() = for {
  d1 <- dice()
  s1 <- get       // Debug get State1
  d2 <- dice()
  s2 <- get       // Debug get State2
  d3 <- dice()
  s3 <- get       // Debug get State3
} yield (d1,d2,d3)

def threeDicet() = for {
  d1 <- dice()
  s1 <- get       // Debug get State1
  d2 <- dice()
  s2 <- get       // Debug get State2
  d3 <- dice()
  s3 <- get       // Debug get State3
} yield s1 :: s2 :: s3 :: Nil


threeDicet().eval(new Random(10)).zipWithIndex.foreach { case (v,i) =>
  println(s"r$i - $v") }


  threeDice().eval(new Random(10))
//rolls.eval(new Random(12))

val l = List.fill(100)(threeDice())
val l2 = l.sequenceU
val rolls = l2.eval(new Random(10))


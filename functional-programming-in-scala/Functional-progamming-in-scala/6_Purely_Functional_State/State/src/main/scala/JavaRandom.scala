
import fpinscala.chap6.{RNG,SimpleRNG}
import State._
import java.util.Random

/*
* Let's use our State class to hold java.util.Random state as
* well as RNG/SimpleRNG states
*/
object JavaRandom {

  /* Our type now deals with Random */
  type Rand[+A] = State[Random,A]

  def int = State( (r: Random) => (r.nextInt + 1,r))

  def ints(count: Int): Random => (List[Int],Random) = 
    sequence(List.fill(count)(JavaRandom.int))

}


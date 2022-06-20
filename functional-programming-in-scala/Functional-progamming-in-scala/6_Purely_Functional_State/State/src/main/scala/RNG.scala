
import fpinscala.chap6.{RNG,SimpleRNG}
import State._

object Rng {
  /*
  * This is our state action.
  * We are defining a type Rand, this
  * type can take a RNG and can generate an
  * A together with a new RNG
  */
  type Rand[+A] = State[RNG,A]
  /*
  * def int: Rand[Int] = (a: RNG) => a.nextInt
  */
  /*
  * def int: Rand[Int] = (a: RNG) => a.nextInt
  */
  def int = State( (r: RNG) => r.nextInt)

  def ints(count: Int): RNG => (List[Int],RNG) = 
    sequence(List.fill(count)(Rng.int))

}



import java.util.concurrent._
import Par._

/**
 * Section 7.3 - parallel word count implemented in terms of Par
 */
object WordCount {

  /**
   * A sequential word count using standard map/reduce logic.
   */
  def wordCount(ps: List[String]): Int = 
    ps.map(wc).reduce(_+_)
  

  /**
   * My first attempt at a parWordCount.
   * This works but creates its own Executor so
   * that the result can be obtained from the parMap
   * to pass onto the parallel sum.
   */
  def parWordCountTry1(ps: List[String]): Int = {
    val es = Executors.newCachedThreadPool
    val c = parMap(ps){wc}(es).get.toIndexedSeq
    sum(c)(es).get
  }

  /**
   * This version is better.
   * The word count part (map) of the computation is 
   * executed in parallel, but the adding of the individual
   * counts (reduce) runs sequentially.
   * I tried doing 
   * {{ map(c){sum(_)} }}
   * 
   * but this returns a Par[Par[Int]] which is messy. 
   */
  def parWordCount(ps: List[String]): Par[Int] = {
    val c: Par[List[Int]] = parMap(ps){wc}
    
    map(c){_.reduce(_+_)}
  }

  /**
   *
   * Helper word count function that is used in all three versions
   * of WordCount above.
   */
  def wc(s: String): Int = 
    if (s.trim == "") 0 
    else s.trim.split("""[^a-zA-Z-'"_0-9]+""").length
}




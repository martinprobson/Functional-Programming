import Par._
import java.util.concurrent._

/**
 * Exercise 7.9 - Show that any fixed size thread pool can be
 *                made to deadlock given the implementation of
 *                fork in BasicPar.scala
 */
object Exercise79 extends App {
  
  /**
   * Helper function to nest fork calls n times
   */
  def nestFork[A](a: => Par[A])(n: Int): Par[A] =  n match {
    case 0 =>  a
    case _ =>  nestFork(fork(a))(n -1)
  }
  
  /* 
   * This set block of code will not deadlock as we are
   * using a FixedThreadPool of size i and nesting fork calls
   * i - 1 times, so we always have enough threads
   */
  Range(2,10).foreach{
    (i: Int) => {
      val e = Executors.newFixedThreadPool(i)
      try {
        println(s"FixedThreadPool size = ${i}, number of nested fork calls = ${i-1}")
        val res = nestFork(unit(42 + 1))(i-1)(e).get(10,TimeUnit.MILLISECONDS)
        println("OK")
      } catch {
        case ex: TimeoutException => println("TimeOutException")
        case ex: Exception => throw ex
      } finally {
        e.shutdownNow
      }
  }}
  	 
  /* 
   * This set block of code will timeout on a deadlock because
   * we have a thread pool size equal to the number of nested forks
   * but we always need one more than this because of the implementation of 
   * fork in BasicPar.
   */
  Range(2,10).foreach{
    (i: Int) => {
      val e = Executors.newFixedThreadPool(i)
      try {
        println(s"FixedThreadPool size = ${i}, number of nested fork calls = ${i}")
        val res = nestFork(lazyUnit(42 + 1))(i)(e).get(10,TimeUnit.MILLISECONDS)
        println("OK")
      } catch {
        case ex: TimeoutException => println("TimeOutException")
        case ex: Exception => throw ex
      } finally {
        e.shutdownNow
      }
  }}
}

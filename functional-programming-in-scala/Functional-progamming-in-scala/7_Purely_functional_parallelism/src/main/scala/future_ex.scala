import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.{Future,Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
 
/**
 * Example usage of the scala Future class
 */
object FutureExample extends App {
  val lastInteger = new AtomicInteger
  def futureInt() = Future {
    println("Running")
    Thread sleep 2000
    lastInteger.incrementAndGet
  }
   
  // use callbacks for completion of futures
  println("a1")
  val a1 = futureInt
  println("a2")
  val a2 = futureInt
  println("foreach")
  a1.foreach {
      case i1 => {
	a2.foreach {
	  case i2 => println("Sum of values is " + (i1 + i2))
	}
      }
  }
  Thread sleep 3000
   
  // use for construct to extract values when futures complete
  val b1 = futureInt
  val b2 = futureInt
  for (i1 <- b1; i2 <- b2) yield println("Sum of values is " + (i1 + i2))
  Thread sleep 3000
   
  // wait directly for completion of futures
  val c1 = futureInt
  val c2 = futureInt
  println("Sum of values is " + (Await.result(c1, Duration.Inf) +
    Await.result(c2, Duration.Inf)))
}

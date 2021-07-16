import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object FutureProblem extends App {
  val f1 = {
    val r = new Random(0L)
    val x = Future(r.nextInt)
    for {
      a <- x
      b <- x
    } yield (a,b)
  }

  val r = new Random(0L)
  val f11 = Future(r.nextInt) flatMap (a => Future(r.nextInt) map (b => (a,b)))
  val f: Future[(Int, Int)] = Future(r.nextInt) flatMap ((a: Int) => Future(r.nextInt) map (b => (a,b)))
   
  val f2: Future[(Int, Int)] = {
    val r = new Random(0L)
    for {
      a <- Future(r.nextInt)
      b <- Future(r.nextInt)
    } yield (a,b)
  }

  f1.onComplete(println)
  f2.onComplete(println)
  f11.onComplete(println)
}


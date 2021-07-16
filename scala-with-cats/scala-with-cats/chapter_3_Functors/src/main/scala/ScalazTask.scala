import scalaz.concurrent.Task
import scala.util.Random

object ScalazTask extends App {
  val t1 = {
    val r = new Random(0L)
    val x = Task.delay(r.nextInt)
    for {
      a <- x
      b <- x
    } yield (a,b)
  }
   
  val t2 = {
    val r = new Random(0L)
    for {
      a <- Task.delay(r.nextInt)
      b <- Task.delay(r.nextInt)
    } yield (a,b)
  }

  val t3 = {
    val r = new Random(0L)
    Task.delay(r.nextInt).flatMap(a => Task.delay(r.nextInt).map(b => (a,b)))
  }

  println(t1.run)
  println(t2.run)
  println(t3.run)

}



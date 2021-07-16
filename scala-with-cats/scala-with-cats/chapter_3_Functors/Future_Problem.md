# Futures are not referentially transparent

Future always computes and caches a result and there's no way for us to tweak this behaviour. This mean we get unpredictable results when we use Future to wrap side-effecting computations. For example: -

```scala
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
   
  val f2 = {
    val r = new Random(0L)
    for {
      a <- Future(r.nextInt)
      b <- Future(r.nextInt)
    } yield (a,b)
  }

  f1.onComplete(println)
  f2.onComplete(println)
}
```

f1 and f2 will return different results because f1 calls nextInt once and f2 calls it twice. 

Scalaz's Task object does not suffer from this problem: -


```
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

  println(t1.run)
  println(t2.run)

}
```




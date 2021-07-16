import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

def hello = {
  Thread.sleep(1000L)
  println("Hello")
}

val a = Future(hello)

Await.result(a, 10.seconds)
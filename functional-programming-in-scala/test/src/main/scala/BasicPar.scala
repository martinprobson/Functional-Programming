
import java.util.concurrent._

object Par {

  type Par[A]= ExecutorService => Future[A]

  /**
    * Unit is represented by a function that returns a UnitFuture, which is
    * a simple implementation of Future that just wraps a constant value.
    * It doesn't use the ExecutorService at all. It is always done and
    * can't be cancelled. Its get method simply returns the value that
    * we give it.
    */
  def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone = true
    def get(timeout: Long, units: TimeUnit) = get
    def isCancelled = false
    def cancel(evenIfRunning: Boolean): Boolean = false
  }

  /**
    * map2 does not evaluate the call to f in a separate logical
    * thread, in accord with our design choice of having fork be
    * the sole function of the API for contolling parallelism.
    * We can always do fork(map2(a,b)(f)) if we want the evaluation
    * of f to occur in a separate thread.
    */
  def map2[A,B,C](a: Par[A],b: Par[B])(f: (A,B) => C): Par[C] =
    (es: ExecutorService) => {
      val af = a(es)
      val bf = b(es)
      UnitFuture(f(af.get, bf.get))
    }

  /**
    * This is the simplest and most natural method of implementing fork.
    * But there are problems with it.
    */
  def fork[A](a: => Par[A]): Par[A] =
    es => es.submit(new Callable[A] {
      def call = a(es).get
    })
}


object Main extends App {

  def mult(a: Int*) = a.foldLeft(1)(_*_)
  def sum(a: Int*) = a.foldLeft(0)(_+_)


    val es: ExecutorService = Executors.newSingleThreadExecutor()

    // res0: Par.Par[Int] = Par$$$Lambda$4268/21318106@aacbd6
    println(s"Par.unit(mult(10,10)) = ${Par.unit(mult(10,10))}")

    val g = Par.fork(Par.map2(Par.unit(mult(10,10)),Par.unit(mult(10,10)))( _ + _))(es).get(1,TimeUnit.SECONDS)
    println(s"g = $g")
    val e = Par.fork(Par.unit(mult(10,10)))(es).get(1,TimeUnit.SECONDS)
    println(s"e = $e")
    println(mult(10,10))
    println(sum(10,10))
    es.shutdown()
}




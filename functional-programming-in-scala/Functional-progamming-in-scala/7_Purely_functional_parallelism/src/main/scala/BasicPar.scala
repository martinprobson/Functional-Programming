
import java.util.concurrent._

object Par {

  /**
   * The type Par is a function that expects an
   * ExecutorService and returns a Future[A]
   */
  type Par[A]= ExecutorService => Future[A]

  /**
   *
   */
  def run[A](es: ExecutorService)(a: Par[A]): Future[A]  = a(es)
  
  /**
   * Exercise 7.4 - Write a function to convert any function A => B to 
   *                one that evaluates its result synchronously.
   */
  def asyncF[A,B](f: A => B): A => Par[B] = (a: A) => lazyUnit(f(a))

  /**
   * Unit is represented by a function that returns a UnitFuture, which is
   * a simple implementation of Future that just wraps a constant value.
   * It doesn't use the ExecutorService at all. It is always done and
   * can't be cancelled. Its get method simply returns the value that
   * we give it.
   */
  def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

  /**
   * We can make a lazy (asyncronous) unit simply by wrapping unit in a 
   * fork
   *
   * es => es.submit( new Callable[A] { call = ((es: ExecutorService) => UnitFuture(a))(es).get })
   */
  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  /** 
   *  UnitFuture is a very simple implementation of future, that
   *  does no async processing, does not use an ExecutorService at
   *  all (although one still has to be passed in to match the contract
   *  of Par), is always done and can't be cancelled.
   */
  case class UnitFuture[A](get: A) extends Future[A] {
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

  /**
   * map is simply defined in terms of map2.
   */
  def map[A,B](pa: Par[A])(f: A => B): Par[B] =
    map2(pa, unit(()))((a,_) => f(a))

  /**
   * parMap maps elements of a List in parallel
   */
  def parMap[A,B](ps: List[A])(f: A => B): Par[List[B]] = fork {
    val fbs: List[Par[B]] = ps.map(asyncF(f))
    sequence(fbs)
  }

  /**
   * Exercise 7.5 - Write a function called sequence.
   *                No additional primatives are required
   *                do not call run.
   */
  def sequence[A](ps: List[Par[A]]): Par[List[A]] =
    es => {
      UnitFuture(ps.foldRight(Nil: List[A])( (a: Par[A],b: List[A]) => {
        val a1 = a(es).get
        a1 :: b }))
    }

  /**
   * Exercise 7.6 - Implement parFilter, which filters
   *                elements of list in parallel.
   */
  def parFilter[A](as: List[A])(f: A => Boolean) = {
    val l: List[Par[List[A]]] = 
             as map (asyncF{ (a: A) => if (f(a)) List(a) else List() })
    map(sequence(l))(_.flatten)
  }

  /**
   * Section 7.3 - Is there a more general version of the parallel sum function
   *               we wrote at the beginning of the chapter? Try using it to find
   *               the maximum value of an IndexedSeq in parallel.
   */
  def parReduce[A](z: A,is: IndexedSeq[A])(f: (A,A) => A): Par[A] = {
    if (is.size <= 1)
      unit(is.headOption getOrElse z)
    else {
      val (l,r) = is.splitAt(is.length/2)
      map2(fork(parReduce(z,l)(f)),fork(parReduce(z,r)(f)))(f)
    }
  }

  /**
   * Section 7.4.1 - Checking if two Par expressions are equal,
   * we define equal as being p.get == p2.get where p and p2 are
   * Par objects.
   */
  def equal[A](e: ExecutorService)(p: Par[A], p2: Par[A]): Boolean =
    p(e).get == p2(e).get
  
  /**
   * parallel sum function written in terms of parReduce
   */
  def sum(ints: IndexedSeq[Int]): Par[Int] = 
    parReduce(0,ints)(_+_)

  /**
   * parallel sum function written in terms of parReduce
   */
  def sum(ints: List[Int]): Par[Int] =
    sum(ints.toIndexedSeq)
    
  /**
   * parallel max function written in terms of parReduce
   */
  def max(ints: IndexedSeq[Int]): Par[Int] = 
    parReduce(Int.MinValue,ints)(Math.max(_,_))
}


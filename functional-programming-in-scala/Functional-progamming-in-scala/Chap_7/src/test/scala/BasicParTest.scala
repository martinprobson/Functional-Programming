
import java.util.concurrent.{ExecutorService,Executors,Callable}
import org.scalatest.fixture

import Par._

class BasicParTest extends fixture.FlatSpec  {

  /**
   * Utility function - return true if passed integer is prime.
   */
  def isPrime(num: Int):Boolean =
    (num > 1) && !(2 to scala.math.sqrt(num).toInt).exists(x =>num % x == 0)
  
  type FixtureParam = ExecutorService
  
  /**
   * All the tests should share the same ExecutorService, this can
   * be done with a OneArgTest fixture.
   * @see [[http://www.scalatest.org/user_guide/sharing_fixtures#withFixtureOneArgTest withFixtureOneArgTest]]
   * 
   */
  def withFixture(test: OneArgTest) = {
    val es = Executors.newCachedThreadPool

    try {
      withFixture(test.toNoArgTest(es))
    } finally es.shutdown
  }

  
  "A call to unit" should "simply return its parameter when called with an ExecutorService and get" in {
    es => 
    val res = unit(1)(es).get
    assert(res === 1)
  }

  "A call to lazyUnit" should "simply return its parameter when called with an ExecutorSerice and get" in {
    es =>
    val es = Executors.newCachedThreadPool()
    val res = lazyUnit(1)(es).get
    assert(res === 1)
  }
  it should "return the same value as if unit were used" in {
    es =>
    assert(lazyUnit("foo")(es).get == unit("foo")(es).get)
  }
   
  "A fork on the sum of a single element integer Vector" should "return that integer" in {
    es =>
      val res = fork(sum(Vector(1)))(es).get
      assert(res === 1)
  }

  
  "A sum on Vector(1,2)" should "return 3" in {
    es =>
      val res = sum(Vector(1,2))(es).get
      assert(res === 3)
  }
  it should "be the same result with map2 expanded" in {
    es =>
      val orig = sum(Vector(1,2))(es).get
      val withMap2 = map2(fork(unit(1)),fork(unit(2)))(_+_)(es).get
      assert(withMap2 === orig)
  }
  it should "be the same result with map2/unit expanded" in {
    es =>
      val orig = sum(Vector(1,2))(es).get
      val withUnit = map2(fork( (es: ExecutorService) => UnitFuture(1)),fork( (es: ExecutorService) => UnitFuture(2)))(_+_)(es).get
      assert(withUnit === orig)
  }
  it should "be the same result with map2/unit/fork expanded" in {
    es =>
      val orig = sum(Vector(1,2))(es).get
      val full = map2((es: ExecutorService) => es.submit( new Callable[Int] { def call =  ((es: ExecutorService) => UnitFuture(1))(es).get }),fork( (es: ExecutorService) => UnitFuture(2)))(_+_)(es).get
      assert(full === orig)
  }
  
  "A sum on Vector(1,2,3,4)" should "return 10" in {
    es =>
      val res = fork(sum(Vector(1,2,3,4)))(es).get
      assert(res === 10)
  }
  it should "be the same result when the call sequence is expanded" in {
    es =>
      val orig = fork(sum(Vector(1,2,3,4)))(es).get
      val expand = map2(map2((es: ExecutorService) => es.submit( new Callable[Int] { def call =  ((es: ExecutorService) => UnitFuture(1))(es).get }),fork( (es: ExecutorService) => UnitFuture(2)))(_+_),
                   map2((es: ExecutorService) => es.submit( new Callable[Int] { def call =  ((es: ExecutorService) => UnitFuture(3))(es).get }),fork( (es: ExecutorService) => UnitFuture(4)))(_+_))(_+_)(es).get             
      assert(orig === expand) 
  }
  
  "Execise 7.4 asyncF test" should "convert any function to run asyncronously and return the same result" in {
    es =>
      def sumSeq(ints: Int*) = (0 /: ints){_+_}  // Define a simple function for our test      
      val nonAsync = sumSeq(1,2,3,4,5)
      val asyncSum = Par.asyncF(sumSeq)
      val asyncres = asyncSum(Seq(1,2,3,4,5))(es).get
      assert(asyncres === nonAsync)
  }

  "Execise 7.5 parMap" should "operate correctly with sequence" in {
    es =>
      val l = (1 to 10).toList
      val res = parMap(l){_*2}(es).get
      assert(res === (1 to 10).toList.map{_*2})
  }
  
  "Execise 7.6 parFilter" should "return list of all prime numbers up to 1000 when passed isPrime function" in {
    es =>
      val l = (1 to 1000).toList
      val res = parFilter(l){isPrime}(es).get
      assert(res === l.filter(isPrime))
  }

  "A max on Vector(-10,0,999,1,2,3,4)" should "return 999" in {
    es =>
      val res = fork(max(Vector(-10,0,999,1,2,3,4)))(es).get
      assert(res === 999)
  }

  "When passed unit(1) and map(unit(1){_+1} equal" should "return true" in {
    es =>
      val res = equal(es)(map(unit(1)){_+1},unit(2))
      assert(res === true)
  }

  "When passed unit(1) and map(unit(2){_+1} equal" should "return false" in {
    es =>
      val res = equal(es)(map(unit(2)){_+1},unit(2))
      assert(res === false)
  }
}


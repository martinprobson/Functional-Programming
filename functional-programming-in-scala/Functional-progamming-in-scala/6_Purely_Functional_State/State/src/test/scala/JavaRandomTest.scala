
import org.scalatest.FunSuite
import java.util.Random
import JavaRandom._

class JavaRandomTest extends FunSuite {

  test("1: JavaRandomTest: Sequence three int") {
    val exp = List(304908422, -1879439975, -1155099827)
    assert(State.sequence(List(int,int,int))(new Random(3))._1 === exp)
  }

  test("2: JavaRandomTest: generate 10 ints") {
    val exp = List(-634239372, 976394919, -1824734167, -995758197, -1795872891, 288278257, -836442133, 304908422, -1879439975, -1155099827)
    assert(ints(10)(new Random(3))._1 === exp)
  } 

  test("3: for vs flatMap/map they are the same thing!!") {
    val three = for {
      r1 <- int
      r2 <- int
      r3 <- int } yield (r1,r2,r3)
    val three2 = int.flatMap( r1 => int.flatMap( r2 => int.map( r3 => (r1,r2,r3))))
    assert(three.run(new Random(100))._1 === three2.run(new Random(100))._1)
  }
}


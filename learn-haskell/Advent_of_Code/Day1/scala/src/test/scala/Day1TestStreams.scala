import org.scalatest.funsuite.AnyFunSuite
import streams.Day1Streams

import java.io.InputStream

class Day1TestStreams extends AnyFunSuite {

  val day1 = new Day1Streams

  test("day1 with puzzleInput should return 646779") {
    assert(day1.day1(getInputData("/puzzleInput.txt")) == 646779)
  }

  test("day1 with exampleInput should return 514579") {
    assert(day1.day1(getInputData("/exampleInput.txt")) == 514579)
  }

  test("day1_part2 with puzzleInput should return 246191688") {
    assert(day1.day1_part2(getInputData("/puzzleInput.txt")) == 246191688)
  }

  test("day1_part2 with exampleInput should return 241861950") {
    assert(day1.day1_part2(getInputData("/exampleInput.txt")) == 241861950)
  }

  test("day1_part2 with test should return ") {
    assert(day1.day1_part2(getInputData("/test.txt")) == 6084270)
  }

  private def getInputData(name: String): Seq[Int] = {
    val is: InputStream = getClass.getResourceAsStream(name)
    scala.io.Source.fromInputStream(is).getLines().toSeq.map(_.toInt)
  }
}

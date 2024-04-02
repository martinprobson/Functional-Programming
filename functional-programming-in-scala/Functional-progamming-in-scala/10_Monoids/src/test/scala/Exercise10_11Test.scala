import Exercise10_11.count
import Exercise10_9.*
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

import java.util.concurrent.Executors


class Exercise10_11Test extends AnyFunSuite with BeforeAndAfterAll with Logging {

  case class TestCondition(expectedResult: Int, input: String)

  /** The test conditions (in file CountWordsTest.txt are in the format: -
   *
   * {expected word count}|{one of more lines of text} ##
   *
   * This code parses the file into a List of TestConditions.
   */
  def tests: List[TestCondition] = {
    io.Source
      .fromResource("CountWordsTest.txt")
      .mkString
      .split("##")
      .toList
      .map((s: String) => {
        val f = s.split("\\|")
        TestCondition(
          "\\n".r.replaceAllIn(f(0), "").toInt,
          f(1)
            .split("(\\n|\\n\\r){2}")
            .filter(_ != "")
            .map("[\\n\\r]".r.replaceAllIn(_, " "))
            .mkString
        )
      })
  }

  private lazy val es = {
    val threadFactory = Thread.ofVirtual().name("virtual-", 0).factory()
    Executors.newThreadPerTaskExecutor(threadFactory)
  }

  override def afterAll(): Unit = es.shutdown()

  tests.foreach {
    case TestCondition(expResult, input) =>
      val display = input.take(100)
      logger.info(s"TestCondition = $expResult - $input")
      test(s"wordCount($display) == $expResult") {
        assertResult(expResult)(count(input))
      }
  }
}

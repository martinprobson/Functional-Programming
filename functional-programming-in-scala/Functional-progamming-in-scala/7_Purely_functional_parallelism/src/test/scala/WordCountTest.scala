import java.util.concurrent.Executors
import org.scalatest.*
import WordCount.*
import org.scalatest.funsuite.AnyFunSuite

class WordCountTest extends AnyFunSuite with BeforeAndAfterAll {

  private def es() = {
    val threadFactory = Thread.ofVirtual().name("virtual-", 0).factory()
    Executors.newThreadPerTaskExecutor(threadFactory)
  }

  override def afterAll(): Unit = es().shutdown()

  case class TestCondition(expectedResult: Int, input: List[String])

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
            .toList
        )
      })
  }

  /** First the sequential word count.
    */
  tests.foreach {
    case TestCondition(expResult, input) =>
      val display = input.take(100)
      test(s"wordCount($display) == $expResult") {
        assertResult(expResult)(wordCount(input))
      }
  }

  /** Now the parWordCountTry1 function.
    */
  tests.foreach {
    case TestCondition(expResult, input) =>
      val display = input.take(100)
      test(s"parWordCountTry1($display) == $expResult") {
        assertResult(expResult)(parWordCountTry1(input))
      }
  }

  /** and the parWordCount function. This is the only one that actually needs an
    * Executor to be passed in
    */
  tests.foreach {
    case TestCondition(expResult, input) =>
      val display = input.take(100)
      test(s"parWordCount($display) == $expResult") {
        assertResult(expResult)(parWordCount(input)(es()).get)
      }
  }

  test("Sequential wordCount on HOD.txt") {
    val t: List[String] = io.Source
      .fromResource("HOD.txt")
      .mkString
      .split("([\\n\\r]){2}")
      .filter(_ != "")
      .toList
    assert(wordCount(t) === 38947)
  }

  test("parallel parWordCountTry1 on HOD.txt") {
    val t: List[String] = io.Source
      .fromResource("HOD.txt")
      .mkString
      .split("([\\n\\r]){2}")
      .filter(_ != "")
      .toList
    assert(parWordCountTry1(t) === 38947)
  }

  test("parallel parWordCount on HOD.txt") {
    val t: List[String] = io.Source
      .fromResource("HOD.txt")
      .mkString
      .split("([\\n\\r]){2}")
      .filter(_ != "")
      .toList
    assert(parWordCount(t)(es()).get === 38947)
  }

}

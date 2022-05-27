import cats.{CatsMain, SourceFileDoesNotExist, WrongArgumentCount}
import org.scalatest.funsuite.AsyncFunSuite
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.*
import common.Logging
import org.scalatest.ParallelTestExecution
import org.scalatest.matchers.should.Matchers
import streams.Day1Streams

class Day1TestCats
    extends AsyncFunSuite
    with AsyncIOSpec
    with ParallelTestExecution
    with Logging
    with Matchers {

  val day1 = new Day1Streams

  test("validate with no arguments should return WrongArgumentCount error") {
    CatsMain
      .validate(List())
      .asserting(_ shouldBe Left(WrongArgumentCount("Expected one argument but got 0")))
  }

  test("validate with too many arguments should return WrongArgumentCount error") {
    CatsMain
      .validate(List("foo", "bar"))
      .asserting(_ shouldBe Left(WrongArgumentCount("Expected one argument but got 2")))
  }

  test("validate with invalid filename should SourceFileDoesNotExist error") {
    CatsMain
      .validate(List("foo"))
      .asserting(_ shouldBe Left(SourceFileDoesNotExist("Source file: foo does not exist")))
  }

  test("run validate with valid filename should return the filename") {
    val file = getClass.getResource("/puzzleInput.txt").getFile
    CatsMain.validate(List(file)).asserting(_ shouldBe Right(file))
  }

  test("run day1 with no arguments should return error ExitCode") {
    CatsMain.run(List()).asserting(_ shouldBe ExitCode.Error)
  }

  test("run day1 with invalid filename should return error ExitCode") {
    CatsMain.run(List("junk")).asserting(_ shouldBe ExitCode.Error)
  }

  test("run day1 with valid filename should return success ExitCode") {
    val file = getClass.getResource("/puzzleInput.txt").getFile
    CatsMain.run(List(file)).asserting(_ shouldBe ExitCode.Success)
  }

}

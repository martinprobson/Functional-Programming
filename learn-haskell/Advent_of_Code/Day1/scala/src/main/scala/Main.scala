import Control.using
import cats.effect.IO
import common.Logging
import streams.Day1Streams

object Main extends App with Logging {

  val day1 = new Day1Streams

  using(scala.io.Source.fromFile(args(0))) { source =>
    {
      val s = source.getLines().toSeq.map(_.toInt)
      println(day1.day1(s))
      println(day1.day1_part2(s))
    }
  }

}

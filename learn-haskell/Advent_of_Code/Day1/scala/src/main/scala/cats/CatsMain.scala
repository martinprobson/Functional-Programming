package cats

import cats.effect.{ExitCode, IO, IOApp, Resource}
import common.Logging
import cats.data.EitherT
import cats.effect.std.Console
import cats.effect.*
import cats.syntax.all.*
import streams.Day1Streams

import java.io.{File, FileInputStream}
import scala.io.{BufferedSource, Source}

object CatsMain extends IOApp with Logging {

  def validate(args: List[String]): IO[Either[DomainError, String]] = {
    if (args.length != 1)
      IO(Left(WrongArgumentCount(s"Expected one argument but got ${args.length}")))
    else {
      val source = new File(args.head)
      if (!source.exists())
        IO(Left(SourceFileDoesNotExist(s"Source file: ${args.head} does not exist")))
      else
        IO(Right(args.head))
    }
  }

  def day1(args: List[String]): IO[Either[DomainError, Int]] =
    (for {
      filename <- EitherT(validate(args))
      s <- EitherT(input(filename))
      ans <- EitherT(day1IO(s))
    } yield ans).value

  def input(f: String): IO[Either[DomainError, Seq[Int]]] =
    IO(scala.io.Source.fromFile(f)).bracket { bs =>
      IO(Right(bs.getLines().toSeq.map(_.toInt)))
    } { bs =>
      IO(bs.close()).handleErrorWith(_ => IO.unit)
    }

  val day1 = new Day1Streams
  def day1IO(s: Seq[Int]): IO[Either[DomainError, Int]] = IO(Right(day1.day1(s)))
  def day1IO_part2(s: Seq[Int]): IO[Either[DomainError, Int]] = IO(Right(day1.day1_part2(s)))

  def file(name: String): Resource[IO, BufferedSource] =
    Resource.make {
      IO.blocking(Source.fromFile(name))
    } { b =>
      IO.blocking(b.close()).handleErrorWith(_ => IO.unit)
    }

  override def run(args: List[String]): IO[ExitCode] =
    day1(args).flatMap({
      case Left(err)   => Console[IO].errorln(err.message) >> IO(ExitCode.Error)
      case Right(name) => IO.println(s"Result is: $name") >> IO(ExitCode.Success)
    })

}

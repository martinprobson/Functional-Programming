import cats._
import cats.arrow.FunctionK
import cats.free.Free
import cats.free.Free.liftF
import scala.io.StdIn.readLine

object FreeMonadConsole extends App {
    sealed trait Console[T]

    case class PutLine(s: String) extends Console[Unit]
    case class GetLine() extends Console[String]

    def putLn(s: String): Free[Console,Unit] =
        liftF[Console, Unit](PutLine(s))

    def getLn: Free[Console,String] =
        liftF[Console, String](GetLine())

    def program: Free[Console,Unit] =
        for {
            _ <- putLn("Hello world 1!")
            _ <- putLn("Hello world 2!")
            _ <- putLn("Hello world 3 !")
            _ <- putLn("Hello world 4!")
            _ <- putLn("Hello world 5!")
            _ <- putLn("Hello world 6!")
        } yield ()

    def compiler: FunctionK[Console,Id] = {
        new FunctionK[Console, Id] {

            override def apply[A](fa: Console[A]): Id[A] = fa match {
                case PutLine(s) =>
                    println(s)
                    () 
                case GetLine() =>
                    readLine()
            }
        }
    }

    def compiler2: FunctionK[Console,Eval] =
        new FunctionK[Console, Eval] {

            override def apply[A](fa: Console[A]): Eval[A] = fa match {
                case PutLine(s) =>
                    Eval.later(println(s))
                case GetLine() =>
                    Eval.later(readLine())
            }
        }

    val result = program.foldMap(compiler2)
    println(result.value)
}

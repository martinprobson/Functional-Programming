import cats._
import cats.arrow.FunctionK
import cats.free.Free
import cats.free.Free.liftF

object FreeMonadCalc extends App {
    sealed trait Calc[A]

    case class Add(i: Int, j: Int) extends Calc[Int]
    case class Subtract(i: Int, j: Int) extends Calc[Int]
    case class Multiply(i: Int, j: Int) extends Calc[Int]


    def add(i: Int, j: Int): Free[Calc,Int] =
        liftF[Calc,Int](Add(i,j))
    def sub(i: Int, j: Int): Free[Calc,Int] =
        liftF[Calc,Int](Subtract(i,j))
    def mult(i: Int, j: Int): Free[Calc,Int] =
        liftF[Calc,Int](Multiply(i,j))

    def program: Free[Calc,Int] =
        for {
            r <- add(1,2)
            r1 <- add(r,2)
            r2 <- add(r1,r)
            r3 <- sub(10,r2)
            r4 <- mult(r3,r3)
            r5 <- mult(r4,r4)
            r6 <- mult(r5,r5)
            res <- mult(r6,r6)
        } yield res

    def compiler: FunctionK[Calc,Id] =
        new FunctionK[Calc, Id] {

            override def apply[A](fa: Calc[A]): Id[A] = fa match {
                case Add(i,j) =>
                    i + j
                case Subtract(i,j) =>
                    i - j
                case Multiply(i,j) =>
                    i * j
            }
        }

    def compiler2: FunctionK[Calc,Eval] =
        new FunctionK[Calc, Eval] {

            override def apply[A](fa: Calc[A]): Eval[A] = fa match {
                case Add(i,j) =>
                    Eval.defer(Eval.now(i + j))
                case Subtract(i,j) =>
                    Eval.later(i - j)
                case Multiply(i,j) =>
                   Eval.always( i * j)
            }
        }
    def printer: FunctionK[Calc,Id] =
        new FunctionK[Calc, Id] {

            override def apply[A](fa: Calc[A]): Id[A] = fa match {
                case Add(i,j) =>
                    println(s"Add($i,$j)")
                    i + j
                case Subtract(i,j) =>
                    println(s"Subtract($i,$j)")
                    i - j
                case Multiply(i,j) =>
                    println(s"Multiply($i,$j)")
                    i * j
            }
        }

    program.foldMap(printer)
    val result = program.foldMap(compiler2)
    println(result.value)
}

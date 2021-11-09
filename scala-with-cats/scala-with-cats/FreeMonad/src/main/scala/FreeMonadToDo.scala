import cats._
import cats.arrow.FunctionK
import cats.free.Free
import cats.free.Free._

object FreeMonadToDo extends App {

    sealed trait ToDo[A]
    case class NewTask[A](task: A) extends ToDo[A]
    case class CompleteTask[A](task: A) extends ToDo[A]
    case class GetTasks[A](default: A) extends ToDo[A]

    def newTask[A](task: A): Free[ToDo,A] =
        liftF(NewTask(task))
    def completeTask[A](task: A): Free[ToDo, A] =
        liftF(CompleteTask(task))
    def getTasks[A](default: A): Free[ToDo, A] =
        liftF(GetTasks(default))

    val program: Free[ToDo, Map[String,Boolean]] = for {
        _ <- newTask("Do the first thing")
        _ <- newTask("Do the second thing")
        _ <- newTask("Do the third thing")
        _ <- newTask("Do something else")
        _ <- newTask("Do something else 1")
        _ <- newTask("Do something else 3")
        _ <- completeTask("Do the first thing")
        tsks <- getTasks(Map.empty[String,Boolean])
    } yield tsks

    def compiler: FunctionK[ToDo,Id] = new FunctionK[ToDo,Id] {

        var map: Map[String, Boolean] = Map[String,Boolean]()

        override def apply[A](fa: ToDo[A]): Id[A] = fa match {
            case NewTask(m) => map = map + (m.toString -> false)
            m
            case CompleteTask(m) => map = map + (m.toString -> true)
            m
            case GetTasks(default) => map.asInstanceOf[A]
        }
    }

    val result = program.foldMap(compiler)
    println(result)
}

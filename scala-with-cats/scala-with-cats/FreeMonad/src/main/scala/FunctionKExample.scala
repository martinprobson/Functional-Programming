import cats.arrow.FunctionK


object FunctionKExample extends scala.App {

    def convert: FunctionK[List,Option] =
        new FunctionK[List,Option] {
            override def apply[A](fa: List[A]): Option[A] = fa.headOption
        }

    type EitherOr[A] = Either[Throwable,A]
    def convertEitherToOption: FunctionK[EitherOr,Option] =
        new FunctionK[EitherOr,Option] {
            override def apply[A](fa: EitherOr[A]): Option[A] = fa match {
                case Left(_) => None
                case Right(a) => Some(a)
            }
        }

    def convertOptionToEither: FunctionK[Option,EitherOr] =
        new FunctionK[Option,EitherOr] {
            override def apply[A](fa: Option[A]): EitherOr[A] = fa match {
                case Some(n) => Right(n)
                case None => Left(new Throwable("Nothing there!"))
            }
        }

    //print(convert(List(1,23)))
    println(convertEitherToOption(Right("Martin")))
    println(convertEitherToOption(Left(new Throwable("Whoops!"))))
    println(convertOptionToEither(Some("Martin")))
    println(convertOptionToEither(None))
    println(convertEitherToOption(convertOptionToEither(Some("Martin"))))
    println(convertEitherToOption(convertOptionToEither(None)))

}

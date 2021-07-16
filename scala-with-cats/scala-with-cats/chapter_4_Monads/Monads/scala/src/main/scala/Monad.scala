
trait Monad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A,B](value: F[A])(func: A => F[B]): F[B]

  // Every Monad is also a Functor, as we can define map in terms
  // of flatMap.
  def map[A,B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(a => pure(func(a)))
}

object Monad {

  type Id[A] = A

  implicit val idMonad: Monad[Id] =
    new Monad[Id] {
      def pure[A](a: A): Id[A] =  a
      def flatMap[A,B](value: Id[A])(func: A => Id[B]): Id[B] = func(value)
    }

  implicit val listMonad: Monad[MyList] =
    new Monad[MyList] {
      def pure[A](a: A): MyList[A] = MyList(a)
      def flatMap[A,B](value: MyList[A])(func: A => MyList[B]): MyList[B] = value match {
        case Nil => Nil
        case Cons(h,tl) => MyList.append(func(h),flatMap(tl)(func))
      }
    }

  implicit val optionMonad: Monad[Option] =
    new Monad[Option] {
      def pure[A](a: A): Option[A] = Some(a)
      def flatMap[A,B](value: Option[A])(func: A => Option[B]): Option[B] = value match {
        case None => None
        case Some(a) => func(a)
      }
    }

  implicit val maybeMonad: Monad[Maybe] =
    new Monad[Maybe] {
      def pure[A](a: A): Maybe[A] = Return(a)
      def flatMap[A,B](value: Maybe[A])(func: A => Maybe[B]): Maybe[B] = value match {
        case Raise(e) => Raise(e)
        case Return(a) => func(a)
      }
    }

  import tree._
  implicit val treeMonad: Monad[Tree] =
    new Monad[Tree] {
      def pure[A](a: A): Tree[A] = Leaf(a)
      def flatMap[A,B](a: Tree[A])(func: A => Tree[B]): Tree[B] = a match {
        case Leaf(a) => func(a)
        case Branch(l,r) => Branch(flatMap(l)(func),flatMap(r)(func))
      }
    }
}




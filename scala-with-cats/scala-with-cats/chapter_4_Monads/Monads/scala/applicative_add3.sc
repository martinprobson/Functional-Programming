
import cats.Applicative
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

val add: Int => Int => Int = x => y => x+y
Applicative[Option].pure(add) <*> Some(3) <*> Some(5)
Applicative[Option].pure(add) <*> Some(3) <*> Some(50)


val add4: Int => Int => Int => Int => Int = a => b => c => d => a+b+c+d
Applicative[Option].pure(add4) <*> Some(1) <*> Some(2) <*> Some(3) <*> Some(4)
Applicative[List].pure(add4) <*> List(1) <*> List(2) <*> List(3) <*> List(4)
Applicative[List].pure(add4) <*> List(1,100) <*> List(2) <*> List(3) <*> List(4)

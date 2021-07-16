/*
  This is the standard Scala Either type
 */
val e = Right("fred"): Either[Int,String]
e.isRight
println(e)
val e1 = Left(10): Either[Int,String]
e.flatMap( (s: String) => Right(s.reverse): Either[Int,String])
e1.flatMap( (s: String) => Right(s.reverse): Either[Int,String])
val l = scala.collection.immutable.List(e,e1)
l.map{ e => e.flatMap{ s => Right(s.reverse): Either[Int,String]}}

val either1: Either[String,Int] = Right(10)
//val either2: Either[String,Int] = Right(100)
val either2: Either[String,Int] = Left("whoops")
val either3: Either[String,Int] = Right(1000)

either1.flatMap{ i => either2.flatMap{ j => either3.flatMap{ k => Right(i+j+k): Either[String,Int]}}}

/*
  for comprehension is just syntax sugar for the statement above.
 */
for {
  i <- either1
  j <- either2
  k <- either3
} yield i + j + k

/*
  This is the CATS version of Either, it provides some additional functions such as
  the asRight and asLeft methods.
 */
import cats.syntax.either._

val cats_either1 = 10.asRight[String]
val cats_either2 = 100.asRight[String]
val cats_either3 = 1000.asRight[String]
val cats_whoops = "whoops".asLeft[Int]

for {
  i <- cats_either1
  j <- cats_either2
  k <- cats_either3
} yield i + j + k

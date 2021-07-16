import scala.util.Try

def parseInt(x: String): Try[Int] = Try(x.toInt)

def divide(a: Int, b: Int): Try[Int] = Try(a/b)

def stringDivide(s1: String, s2: String): Try[Int] =
  parseInt(s1).flatMap( a => parseInt(s2).flatMap( b => divide(a,b)))

def stringDivide2(s1: String, s2: String): Try[Int] =
  for {
    a <- parseInt(s1)
    b <- parseInt(s2)
    ans <- divide(a,b)
  } yield ans

stringDivide("10s","1")
def parseInt(s: String): Option[Int] = scala.util.Try(s.toInt).toOption

def divide(a: Int, b: Int): Option[Int] = if (b == 0) None else Some(a/b)


def stringDivide(s1: String, s2: String): Option[Int] =
  parseInt(s1).flatMap( a => parseInt(s2).flatMap(b => divide(a,b)))



def parseInt(str: String): Option[Int] = scala.util.Try(str.toInt).toOption

def divide(a: Int, b: Int): Option[Int] =
         if (b == 0) None else Some(a/b)

def stringDivideBy(aStr: String, bStr: String): Option[Int] =
         parseInt(aStr).flatMap{ a => parseInt(bStr).flatMap{ b => divide(a,b) } }

// or (using for comprehension: -

def _stringDivideBy(aStr: String, bStr: String): Option[Int] =
         for {
                 a <- parseInt(aStr)
                 b <- parseInt(bStr)
                 ans <- divide(a,b)
             } yield ans

stringDivideBy("20","10")
stringDivideBy("20x","10")
stringDivideBy("20","10x")
stringDivideBy("20","0")
_stringDivideBy("20","10")
_stringDivideBy("20x","10")
_stringDivideBy("20","10x")
_stringDivideBy("20","0")


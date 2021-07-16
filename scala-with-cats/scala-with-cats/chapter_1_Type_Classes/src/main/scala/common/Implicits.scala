package common

/*
  times is called an "Extension method" in this case, because we are
  extending the Integer class.
 */
object Implicits {
  implicit class IntegerWithTimes(s: Integer) {
    def times[A](f: => A): Unit = {
      @scala.annotation.tailrec
      def loop(current: Int): Unit = {
        if (current > 0) {
          f
          loop(current - 1)
        }
      }
      loop(s)
    }
  }
  implicit class IntWithTimes(s: Int) {
    def times[A](f: => A): Unit = {
      @scala.annotation.tailrec
      def loop(current: Int): Unit = {
        if (current > 0) {
          f
          loop(current - 1)
        }
      }
      loop(s)
    }
  }
}

import common.Implicits._
object Main extends App {

  // 1. Using implicits
  Integer.valueOf(10).times(println("Hello"))
  // 2. Explicitly....
  IntegerWithTimes(Integer.valueOf(10)).times(println("Hello"))

  // 1. Using implicits
  10 times println("Hello")
  // 2. Explicitly....
  IntWithTimes(10).times(println("Hello"))

  /*
  Int type above does not have a `times` method, so before flagging a compile error, the compiler checks
  for any implicits in scope that might fix the error.
  The implicit class `IntWithTimes` has a times method, so the constructor of this class is used to resolve the
  error, so the statement above becomes: -

  IntWithTimes(10).times(println("Hello"))

   */


  import scala.language.implicitConversions

  /*
      Implicit Conversions are frowned upon
      and have to be turned on with the import above.
      - see the scala doc for scala.language.implicitConversions
   */
  implicit def string2Person(s: String): Person = Person(s,"unknown")
  def printPerson(p: Person): Unit = {
    println(s"common.Person ${p.name} - ${p.email}")
  }
  printPerson(Person("fred","email"))
  printPerson("fred")
}



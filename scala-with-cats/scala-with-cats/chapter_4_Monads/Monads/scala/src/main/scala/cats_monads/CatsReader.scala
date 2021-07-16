package cats_monads

import cats.implicits._
import cats._
import cats.data.Reader

case class Db(
             userNames: Map[Int,String],
             passwords: Map[String, String]
             )

object CatsReader extends App {

  type DbReader[A] = Reader[Db, A]

  def findUserName(userId: Int): Reader[Db,Option[String]] = Reader[Db,Option[String]]( db => db.userNames.get(userId))
  def _findUserName(userId: Int): DbReader[Option[String]] = Reader[Db,Option[String]]( db => db.userNames.get(userId))

  def checkPassword(userName: String, password: String): DbReader[Boolean] = Reader[Db,Boolean](
    db => db.passwords.contains(userName) && db.passwords(userName) == password
  )

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      user <- findUserName(userId)
      success <- user.map{ u => checkPassword(u,password)}.getOrElse(false.pure[DbReader])
    } yield success

  def _checkLogin(userId: Int, password: String): DbReader[Boolean] =
    findUserName(userId).flatMap{ user => user.map{ u => checkPassword(u,password)}.getOrElse(false.pure[DbReader])}

  val users = Map(
    1 -> "Martin",
    2 -> "Sharon",
    3 -> "Gemma",
    4 -> "Natasha"
  )

  val passwords = Map(
    "Martin" -> "martin123",
    "Sharon" -> "sharon123",
    "Gemma" -> "gemma123",
    "Natasha" -> "natasha123"
  )

  val db = Db(users,passwords)
  val res = checkLogin(10,"martin123").run(db)
  val checks = Seq(
    checkLogin(1,"martin123"),
    checkLogin(2,"sharon123"),
    checkLogin(3,"gemma123"),
    checkLogin(4,"natasha123"),
    checkLogin(1,"martin1234"),
    checkLogin(2,"sharon1234"),
    checkLogin(3,"gemma1234"),
    checkLogin(4,"natasha1234"),
    checkLogin(10,"martin1234"),
    checkLogin(20,"sharon1234"),
    checkLogin(30,"gemma1234"),
    checkLogin(40,"natasha1234"),
    _checkLogin(1,"martin123"),
    _checkLogin(2,"sharon123"),
    _checkLogin(3,"gemma123"),
    _checkLogin(4,"natasha123"),
    _checkLogin(1,"martin1234"),
    _checkLogin(2,"sharon1234"),
    _checkLogin(3,"gemma1234"),
    _checkLogin(4,"natasha1234"),
    _checkLogin(10,"martin1234"),
    _checkLogin(20,"sharon1234"),
    _checkLogin(30,"gemma1234"),
    _checkLogin(40,"natasha1234")
  )
  checks.foreach( c => println(c.run(db)))

}


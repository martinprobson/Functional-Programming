package typeclass.json

import common.Person

// This is our type class
trait JsonWriter[A] {
  def write(value: A): Json
}


// These are our type class instances
object JsonWriter {

  implicit val stringWriter: JsonWriter[String] =
    (value: String) => JsString(value)

  implicit val personWriter: JsonWriter[Person] =
    (value: Person) => JsObject(Map(
      "name" -> JsString(value.name),
      "email" -> JsString(value.email)
    ))

  implicit val doubleWriter: JsonWriter[Double] =
    (value: Double) => JsNumber(value)

  implicit val IntegerWriter: JsonWriter[Integer] =
    (value: Integer) => JsNumber(value.toDouble)

  implicit val intWriter: JsonWriter[Int] =
    (value: Int) => JsNumber(value.toDouble)
}


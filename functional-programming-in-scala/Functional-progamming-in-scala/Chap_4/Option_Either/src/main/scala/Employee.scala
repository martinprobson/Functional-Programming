
//
// Functional Programming in Scala
// Chapter 3 - Functional Data Structures
//

import fpinscala.chap4._
import scala.annotation.tailrec

//
// Using Option class
//
case class Employee(name: String, department: String)

object Employee {
  @tailrec
  def lookupByName(name: String, employees: List[Employee]): Option[Employee] = employees match {
    case Nil => None
    case h :: tl => if (h.name == name) Some(h) else lookupByName(name,tl)
  }
}



object Run extends App {
    val emps = List( Employee("Martin","IT"),
                     Employee("Natasha","Art"),
                     Employee("Gemma","History"))

    println(Employee.lookupByName("Martin",emps).map(_.department).getOrElse("Emp not found"))
    println(Employee.lookupByName("Gemma",emps).map(_.department).getOrElse("Emp not found"))
    println(Employee.lookupByName("Martin2",emps).map(_.department).getOrElse("Emp not found"))

}

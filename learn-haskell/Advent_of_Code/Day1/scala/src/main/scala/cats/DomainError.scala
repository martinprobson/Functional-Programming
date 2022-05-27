package cats

sealed abstract class DomainError {
  val message: String
}

case class WrongArgumentCount(message: String) extends DomainError
case class SourceFileDoesNotExist(message: String) extends DomainError

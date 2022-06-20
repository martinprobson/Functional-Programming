
# Monoids

## Introduction
A Monoid is an example of a *purely algebraic* structure, that is it is a structure that is defined only by its
algebra. Other than satisfying the same laws, instances of the Monoid interface may have little or nothing to 
do with each other. Nonetheless, the structure can be used to write useful polymorphic functions.

## Definition
A Monoid consists of the following: -
- Some type `A`
- An associative binary operation, `op`, that takes two values of type A and combines them into one: `op(op(x,y),z) == op(x,op(y,z)` for any choice of `x: A, y: A, z: A`
- A value, `zero: A`, that is the identity for that operation: `op(x, zero) == x` and `op(zero, x) == x` for any `x: A`

## Example Addition
Mathematical addition satisfies the two Monoid laws above. For example: -
- Type A is the set of Integers
- Addition is an associative operation. ( (x + y) + z ) == ( x + (y + z) )
- The identity value is 0. x + 0 = x and 0 + x = x

## Some Example Monoids in Scala

```scala
	trait Monoid[A] {
	  def op(a1: A, a2: A): A
	  def zero: A
	}

	object Main extends App {

	  val intAddition = new Monoid[Int] {
	    def op(a1: Int, a2: Int): Int = a1 + a2
	    def zero = 0
	  }

	  val intMultiplication = new Monoid[Int] {
	    def op(a1: Int, a2: Int): Int = a1 * a2
	    def zero = 1
	  }

	  val stringMonoid = new Monoid[String] {
	    def op(a1: String, a2: String): String = a1 + a2
	    def zero = ""
	  }

	  def listMonoid[A] = new Monoid[List[A]] {
	    def op(a1: List[A], a2: List[A]): List[A] = a1 ::: a2
	    def zero = Nil
	  }

	  val booleanOr = new Monoid[Boolean] {
	    def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2
	    def zero = false
	  }

	  val booleanAnd = new Monoid[Boolean] {
	    def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2
	    def zero = true
	  }


	}
```

## Associative and Commutative Laws
Notice that all the above Monoids are both associative **and** commutative but it is not necessary for the binary operation to be
commutative in order to satisfy the Monoid law. For example the following are all valid Monoids but they are not commutative: -

```scala
  //
  // Note we could also say a2 else a1 here and still satisfy the
  // monoid rule even though the statements are not logically
  // equivalent.
  def optionMonoid[A] = new Monoid[Option[A]] {
    def op(a1: Option[A], a2: Option[A]): Option[A] = {
      a1 orElse a2
    }

    def zero = None
  }

  //
  // We can get the dual of a monoid by flipping the 'op'.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(x: A, y: A) = m.op(y,x)
    val zero = m.zero
  }

  //
  // Then we can have both versions of the Monoid on hand...
  //
  def firstOptionMonoid[A]: Monoid[Option[A]] = optionMonoid[A]
  def lastOptionMonoid[A]: Monoid[Option[A]] = dual(firstOptionMonoid)
```

```scala

  //
  // Note we could also say a1 andThen a2 here and still satisfy the
  // monoid rule even though the statements are not logically
  // equivalent.
  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    def op(a1: A => A, a2: A => A): A => A = {
      a1 compose a2
    }

    def zero = (a: A) => a
  }

  //
  // We can get the dual of a monoid by flipping the 'op'.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(x: A, y: A) = m.op(y,x)
    val zero = m.zero
  }

  //
  // Then we can have both versions of the Monoid on hand...
  //
  def firstendoMonoid[A]: Monoid[A => A] = endoMonoid[A]
  def lastendoMonoid[A]: Monoid[A => A] = dual(firstendoMonoid)
```

# Functional Design Patterns

## Summary
Monads and related concepts, are the functional programming equivalent of object-oriented design patterns - architectural building blocks
that turn up again and again in code. They differ from object-oriented patterns in two ways: -
* they are formally, and thus precisely defined; and
* they are extremely general.

## Monoid and Semigroups
A Monoid of type A is: -
* an operation `combine` with type `(A,A) => A`
* an element `empty` of type A.

A Semigroup is a Monoid without the `empty` element, so it just has the combine element.

```scala
trait SemiGroup[A] {
  def combine(x: A, y: A): A
}


trait Monoid[A] extends SemiGroup[A] {
  def empty: A
}

```

### Monoid Laws
A Monoid must also obey the following laws: -

```scala
def associativeLaw[A](x: A, y: A, z: A)
      (implicit m: Monoid[A]): Boolean = {
  m.combine(x, m.combine(y, z)) ==
    m.combine(m.combine(x, y), z)
}

def identityLaw[A](x: A)
      (implicit m: Monoid[A]): Boolean = {
  (m.combine(x, m.empty) == x) &&
    (m.combine(m.empty, x) == x)
}
```

[This Scala SBT project](chapter_2/monoid) has some definitions of common Monoids and property based testing to verify the Monoid laws against them.


## Functors
Informally, a functor is anything with a `map` method. Formally, a functor is a type `F[A]` with an operation `map` with type `(A => B) => F[B]`.

```scala
import scala.language.higherKinds

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
```


### Functor Laws
A functor must obey the following laws: -

* Calling `map` with the identity function is the same as doing nothing:
```scala
fa.map(a => a) == fa
```

eg.
```scala
List(1,2,3).map{a => a} == List(1,2,3)
```

* *Composition* mapping with two functions `f` and `g` is the same as mapping with `f` and then mapping with `g`:
```scala
fa.map(g(f(_))) == fa.map(f).map(g)
```

eg.
```scala
List(1,2,3).map(x => add1(add2(add3(x)))) == List(1,2,3).map(add3).map(add2).map(add1)
```


## Monads
A monad is a mechanism for *sequencing computations*.

Functors allows us to sequence copmputations ignoring some complication due to the data type (for example, an `Option` value being `None`). However, functors are limited
in that they only allow this complication to occur once at the beginning of the sequence (this is because the function passed to the `map` method is of type `A => B` rather than
`A => F[B]`).

This is where monads come in. A monad's `flatMap` method allows us to specify what happens next, taking into account an intermediate complication. The `flatMap` method of `Option`
takes intermediate `Options` into account. The `flatMap` method of `List` handles intermediate `Lists`. And so on. In each case, the function passed to `flatMap` specifies
the application-specific part of the computation, and `flatMap` itself takes care of the complication allowing us to `flatMap` again.

### Example - Options
`Option` allows us to sequence computations that may or may not return values. For example: 

```scala
def parseInt(s: String): Option[Int] = scala.util.Try(s.toInt).toOption

def divide(a: Int, b: Int): Option[Int] = if (b == 0) None else Some(a/b)


def stringDivide(s1: String, s2: String): Option[Int] =
  parseInt(s1).flatMap( a => parseInt(s2).flatMap(b => divide(a,b)))
```

Each of these methods may fail by returning `None`. The `flatMap` method allows us to ignroe this complication when we sequence operations.

### Definition of a Monad
Monadic behaviour is formally captured in two operations: -

* `pure` of type `A => F[A]`
* `flatMap` of type `(F[A], A => F[B]) => F[B]`

```scala

import scala.language.higherKinds

trait Monad[F[_]] {
 def pure[A](value: A): F[A]
 def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
}
```

### Monad Laws
`pure` and `flatMap` must obey a set of laws that allow us to sequence operations freely without unintended glitches and
side-effects: 

#### Left identity
Calling `pure` and transforming the result with `func` is the same as calling `func`:

```scala
pure(a).flatMap(func) == func(a)
```

#### Right identity
Passing `pure` to `flatMap` is the same as doing nothing:

```scala
m.flatMap(pure) == m
```

#### Associativity
Flatmapping over two functions `f` and `g` is the same as flatmapping over `f` and then flatmapping over `g`:

```scala
m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
```

### Every Monad is also a Functor
Note that every monad is also a Functor, this is because `map` can always be expressed in terms of `flatMap`: -

```scala
def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(a => pure(func(a)))
``` 



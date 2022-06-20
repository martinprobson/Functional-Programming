# Scala For Comprehensions

## Introduction

To help understandability, Scala has a special notation which simplifies common patterns of applications of higher-order functions (map/flatMap and filter). 
This notation builds a bridge between set comprehensions in mathematics and for-loops in imperative languages such as C and Java. It also closely resembles the query notation of relational databases.

For comprehensions look a bit like a for-loop in imperative languages, except that it constructs a list of results for all iterations. 

Generally a for-comprehension is of the form: -
```
 for ( s ) yield e
```

Here *s* is a sequence of *generators, definitions and filters*. A generator is of the form `val x <- e` where e is a list-valued expression. It binds x to successive values in the list.
A definition of of the form, `val x = e`. It introduces x as a name for the value of e in the rest of the comprehension. A filter is an expression f of type Boolean.

## Example 1

Suppose we wanted to solve the following problem: -

> Given a positive integer *n*, find all pairs of positive integers *i* and *j*, where *1 <= j < i < n* such that *i + j* is even.

With flatMap/map and filter, this can be solved as follows: -

```
def evenPairs(n: Int) = 
  List.range(1,5).flatMap( i => List.range(1,i).map( j => (i,j))).filter( pair => (pair._1 + pair._2) % 2 == 0)
```

eg `evenPairs(5)` would return: -

```
List((3,1), (4,2))
```

Using For comprehensions, the **exact** same code can be expressed as: -

```
for { 
  i <- List.range(1, n)
  j <- List.range(1, i)
  if (i+j) % 2 == 0 } yield {i,j}
```

A for comprehension can be used with any data type that supports the higher-order functions, *map, flatMap and filter*.

## Example 2

The *scalaz*  state monad supports the required higher-order functions, so we can write code such as: -

```
	import scalaz.syntax.state._
	import scalaz.State, State._

	sealed trait Aspect
	case object Green extends Aspect
	case object Amber extends Aspect
	case object Red   extends Aspect

	sealed trait Mode
	case object Off      extends Mode
	case object Flashing extends Mode
	case object Solid    extends Mode

	// represents the actual display set: must be enabled before 
	// it can be used. 
	case class Signal(
	    isOperational: Boolean, 
	    display: Map[Aspect, Mode])

	  def enable: State[Signal, Boolean] = 
	    for {
	      a <- init // Initializae an empty state.	
	      _ <- modify((s: Signal) => s.copy(isOperational = true)) // Modify the State to isOperational
	      r <- get // Get the value (Boolean)
	    } yield r.isOperational
```

The enable function could be written as: -

```
	  def enable: State[Signal,Boolean] =
	    init.flatMap( a => modify(  (s:Signal) => s.copy(isOperational = true)) 
		.flatMap( _ => get.map( r => r.isOperational)))
```

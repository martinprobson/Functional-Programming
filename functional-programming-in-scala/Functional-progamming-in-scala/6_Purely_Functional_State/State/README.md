# Chapter 6 - Exercise 6.10

## A Generalized State action type

This project uses some objects from the `FuncState` project, so do a `sbt publishLocal` in the FuncState project so they can be picked up.
 
## Example
 
We start with a case class and a companion object called State2.
The case class encapsulates the state transistion function S => (A,S).

```
package fpinscala.chap6

case class State2[S,+A](run: S => (A,S))

object State2
```

We can encapsulate the random number generator into this model like so: -

```
val randInt = State2[RNG,Int](rng => rng.nextInt)
```

and call it like so: -

```
randInt.run(SimpleRNG(1))
```




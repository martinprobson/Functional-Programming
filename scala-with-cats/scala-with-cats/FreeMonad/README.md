# Free Monads - Examples

## Summary
Some examples of Free Monads: -
* `FreeMonadCalc` - A simple calculator.
* `FreeMonadToDo` - A To-Do list.
* `FreeMonadConsole` - A console algebra.

## Detail
A free monad is of the type: -

```scala
Free[F, A]
```
where: -
* `Free` - is the program.
* F - The program algebra.
* A - The value produced by the program.

### FreeMonadCalc

```scala
Free[Calc, Int]
```

### FreeMonadToDo

```scala
Free[ToDo, Map[String, Boolean]]
```

### FreeMonadConsole

```scala
Free[Console, Unit]
```
### FunctionK
The examples also make use of the `FunctionK` transformation that transform one first-order
higher kinded type (a type that takes a single type parameter such as `List` or `Option`) to another.

### Further Reading
For more details, see: -
* [Free Monad in CATS](https://typelevel.org/cats/datatypes/freemonad.html)
* [Move over Free Monads, make way for Free Applicatives](https://www.youtube.com/watch?v=H28QqxO7Ihc)
* [FunctionK in CATS](https://typelevel.org/cats/datatypes/functionk.html)

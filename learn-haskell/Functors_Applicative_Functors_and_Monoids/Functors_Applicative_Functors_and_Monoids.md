# Functors, Applicative Functors and Monoids

Haskell's combination of purity, higher order functions, parameterized algebraic data types, and type classes allows us to implement polymorphism on a much
higher level than possible in other languages. We don't have to think about types belonging to a big hierarchy of types. Instead we think about what the
types can act like and then connect them with appropriate typeclasses. A `Int` can act like a lot of things. It can act like an equatable thing, like an
ordered thing, like an enumerable thing, etc.

## Functors
`Functor` is characterised by the `fmap` function: -

Haskell
```haskell
class Functor f where
    fmap :: (a -> b) -> f a -> f b
```

Scala
```scala
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
```

If a type is an instance of `Functor`, you can use `fmap` to apply a function to values inside it. Another way of describing `fmap` is that it promotes ("lifts")
functions to act on functorial values. To ensure `fmap` works sanely, any instance of `Functor` must comply with the following two laws: -

Haskell
```haskell
fmap id = id                   -- 1st functor law
fmap (g . f) = fmap g . fmap f -- 2nd functor law
```

eg,

Haskell
```haskell
fmap id [1] == id [1]
True

((fmap negate) . (fmap (+1)) $ [1]) == (fmap (negate . (+1)) [1])
```
Scala
```scala
def id[A](a: A):A = a

List(1,2,3).map(id) == List(1,2,3)
Some(10).map(id) == Some(10)

val add1: Int => Int = _+1
val negate: Int => Int = _*(-1)
List(1,2,3).map(add1).map(negate) == List(1,2,3).map(negate compose add1)
```

`fmap` has a infix synonym of `<$>`: -

Haskell
```haskell
negate <$> [1,2,3]
[-1.-2.-3]
```



### Example Functors

#### IO

Haskell
```haskell
instance Functor IO where
    fmap f action = do
        result <- action
	return (f result)
```

##### Example

Haskell
```haskell
import Data.Char
import Data.List

main = do line <- fmap (intersperse '-' . reverse . map toUpper) getLine
          putStrLn line
```

### RoseTree

#### Haskell

```haskell
--
-- Define and instance of Functor for the following type
--
data Tree a = Node a [Tree a] deriving (Show)

instance Functor Tree where
  fmap f (Node x ts) = Node (f x) (fmap (fmap f) ts) 

--
-- Build a new (single node tree)
new :: a -> Tree a
new x = Node x []

ins :: a -> Tree a -> Tree a
ins x (Node a []) = Node a [Node x []]
ins x (Node a [t] ) = Node a [ins x t]

--
-- Build a sample Tree
--
buildTree:: (Num a, Enum a) => Tree a
buildTree = foldl (flip ins) (new 0) (take 100 [1,2..])

--
-- main
--

main = do
    print  buildTree 
    print $ fmap (*1000)  buildTreehaskell
```

#### Scala

```scala
//
// Define and instance of Functor for the following type
//
sealed trait Tree[+A]

case class Node[A](a: A, r: List[Tree[A]]) extends Tree[A]

object RoseTree {
  def _new[A](a: A): Tree[A] = Node(a, List(): List[Tree[A]])

  // Build a new (single node tree)
  // Note: This is not tail recursive and should be
  def ins[A](a: A)(t : Tree[A]): Tree[A] = (a, t) match {
    case (a, Node(b, List())) => Node(b, List(Node(a, List())))
    case (a, Node(b, List(c))) => Node(b, List(ins(a)(c)))
  }

  //
  // Build a sample tree
  //
  def buildTree: Tree[Int] = (1 to 100)
    .toList
    .foldLeft(_new(0): Tree[Int])( (t: Tree[Int], i: Int) => ins(i)(t))

  def map[A,B](t: Tree[A])(f: A => B): Tree[B] = (t,f) match {
    case (Node(b, List()), f) => Node(f(b), List())
    case (Node(b, c), f) => Node(f(b), c.map( (t: Tree[A]) => RoseTree.map(t)(f)))

  }
}

//
// Main
//
RoseTree.map(RoseTree.buildTree)(_*1000)
```

## Applicative Functors
So far we have been mapping functions that take one parameter. What happens if we map a function that takes two parameters:-

Haskell
```haskell
:t fmap (+) [1,2,3]
fmap (+) [1,2,3] :: Num a => [a -> a]
:t fmap (+) (Just 10)
fmap (+) (Just 10) :: Num a => Maybe (a -> a)
```

Scala
```scala
val add = (x:Int) => (y:Int) => x + y
:t List(1,2,3).map(add)
List[Int => Int]
:t Some(10).map(add)
Option[Int => Int]
```

We end up with a partially applied function wrapped inside the functor. We can then map functions that take these functions as parameters over them: -

Haskell
```haskell
let a = fmap (+) [1,2,3]
fmap (\f -> f 9) a
[10,11,12]
let b = fmap (+) (Just 10)
fmap (\f -> f 9) b
Just 19
```

Scala
```scala
scala> List(1,2,3).map(add).map(f => f(9) )
val res29: List[Int] = List(10, 11, 12)

scala> Some(10).map(add).map(f => f(9) )
val res30: Option[Int] = Some(19)
```

But what if we have a functor value of `Just (3 *)` and a functor value of `Just 5` and we want to take out the function from the first functor and apply it to the second.
We cannot do that with a normal functor, because all they support is just mapping normal functions over existing functors. We can't map a function that is inside
another functor with what `fmap` offers us.

The `Applicative` typeclass allows us to do this in the `Control.Applicative` and it defines two methods, `pure` and `<*>`. It does not provide a default implementation for either of
them, so we have to define them both if we want something to be a applicative functor. The class is defined like: -

Haskell
```haskell
class (Functor f) => Applicative f where
    pure :: a -> f a
    (<*>) :: f (a -> b) -> f a -> f b
```

Scala
```scala
import scala.language.higherKinds
trait Applicative[F[_]] extends Functor[F] {
    def pure[A](a: => A): F[A]
    def <*>[A,B](fa: => F[A])(f: => F[A => B]): F[B]
}
```

Applicative functors allow you to operate on several functors with a single function: -

Haskell
```haskell
pure (+) <*> Just 3 <*> Just 5
Just 8
```

Scala
```scala
import cats.Applicative
import cats.implicits._

val add: Int => Int => Int = x => y => x+y
Applicative[Option].pure(add) <*> Some(3) <*> Some(5)
res0: Option[Int] = Some(8)
```

Scala
```scala
add4: Int => (Int => (Int => (Int => Int))) = <function>
//al add4: Int => Int => Int => Int => Int = a => b => c => d => a+b+c+d
Applicative[Option].pure(add4) <*> Some(1) <*> Some(2) <*> Some(3) <*> Some(4)
//res1: Option[Int] = Some(10)
Applicative[List].pure(add4) <*> List(1) <*> List(2) <*> List(3) <*> List(4)
//res2: List[Int] = List(10)
Applicative[List].pure(add4) <*> List(1,100) <*> List(2) <*> List(3) <*> List(4)
//res3: List[Int] = List(10, 109)
```

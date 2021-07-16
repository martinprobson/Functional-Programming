# Types and Typeclasses

## Introduction
Haskell is a statically typed language with type inference. We can use the GHCI `:t` command to examine the type of any valid expression: -

```haskell
λ> :t 'a'
'a' :: Char
*Main
λ> :t 1 == 2
1 == 2 :: Bool
*Main
λ> :t length'
length' :: [a] -> Int
*Main
λ> :t "Hello"
"Hello" :: [Char]
*Main
λ> :t zip
zip :: [a] -> [b] -> [(a, b)]
*Main
λ> :t cycle
cycle :: [a] -> [a]
*Main
λ> :t if (1 == 2) then True else False
if (1 == 2) then True else False :: Bool
```

* The `::` in the above can be read as **"has type of"**
* Explicit types are always denoted with the first character in uppercase.
* The `->` (function) can be read as **"maps from"**

## Basic Types

| Type      | Description                                            |
| `Int`     | Bounded integer (e.g. max 2147483647 on a 32-bit m/c)  |
| `Integer` | Unbounded integer                                      |
| `Float`   | Single precision floating point                        |
| `Double`  | Double precision floating point                        |
| `Bool`    | Boolean (True/False)                                   |
| `Char`    | Character, a list of characters `[Char]` is a `String` |

## Type Variables
If we examine the type of the `head` function: -

```haskell
:t head
head :: [a] -> a
```

we can see that it contains a `type variable` (a in this case). This means that `a` can be of any type. Functions that have type variables are called **polymorphic functions**.

### Polymorphic Functions
Polymorphic functions allow us to write much more generic code, so, instead of doing this to find the head of a list of ints: -

```haskell
headInt :: [Int] -> Int
headInt [] = error "error"
headInt (x:xs) = x
```

and then having to write the same function for lists of chars, bools etc. We can instead write a polymorphic function with a type variable: -

```haskell
head' :: [a] -> a
head' []        = error "empty list"
head' (x:_)     = x
```

## Type Class
A typeclass is like an interface that describes some behaviour. If a type is part of a typeclass, this means it implements the behaviour that the typeclass describes.
If we look at the type of the `==` function: -

```haskell
:t (==)
(==) :: Eq a => a -> a -> Bool
```

we can see that there is a **class constraint**. This type declaration means the equality function takes any two values of the same type and returns a `Bool`. 
The type of the two values must be a member of the `Eq` typeclass (this is the class constraint).

### Basic Typeclasses
Some of the basic typeclasses supported by Haskell are: -

| Typeclass  | Description                                                                                 |
| `Eq`       | Supports equality testing, members must implement the `==` and `/=` functions               |
| `Ord`      | For types that have ordering                                                                |
| `Show`     | Types that can be presented as Strings                                                      |
| `Read`     | Takes a String and converts to the type that is a member of `Read` (e.g. `read "5" :: Int`) |
| `Enum`     | Types that can be enumerated                                                                |
| `Bounded`  | Types that have a lower and upper bound                                                     |
| `Num`      | Numeric typeclass                                                                           |
| `Integral` | Integral typeclass (Int and Integer)                                                        |
| `Floating` | Floating point typeclass (Float and Double)                                                 |


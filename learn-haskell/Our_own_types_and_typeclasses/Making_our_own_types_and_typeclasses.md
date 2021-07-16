# Making our own Types and Typeclasses

## Algebraic Data Types
We can define our own data types with the `data` keyword, so `Bool` is defined as follows in the standard library: -

```haskell
data Bool = True | False
```

`data` means that we are defining a new type. The part before the `=` denotes the type, which is `Bool`. The 
parts after the `=` are *value constructors*. They specify the different values this type can have. The
`|` is read as or. Both the type name and the value constructors have to be capital cased.

We can define a Shape type that can be a Circle or Rectangle: -

```haskell
data Shape = Circle Float Float Float | Rectangle Float Float Float Float
```

`Circle` and `Rectangle` are value constructors that take three and four `Floats` respectively. Value constructors are actually 
functions that  return a value of the data type: -

```haskell
λ> :t Circle
Circle :: Float -> Float -> Float -> Shape
Prelude
λ> :t Rectangle
Rectangle :: Float -> Float -> Float -> Float -> Shape
```

Because value constructors are functions we can use them the same way as any other function, for example, partial application:-

```haskell
zipWith' (Circle 10) [10,20,30] [10,20,30]
[Circle 10.0 10.0 10.0,Circle 10.0 20.0 20.0,Circle 10.0 30.0 30.0]
```

We can make the `Shape` type a more understandable by using an intermediate type of `Point`

```haskell
module Shape 
  ( Shape(..)
  , Point(..)
  , surface
  ) where

data Shape = Circle Point Float | Rectangle Point Point deriving (Show)
data Point = Point Float Float deriving (Show)

surface :: Shape -> Float
surface (Circle _ r) = pi * r * 2
surface (Rectangle (Point x1 y1) (Point x2 y2)) = (abs $ x2 - x1) * (abs $ y2 - y1)
```

```haskell
λ> :t surface
surface :: Shape -> Float
*Shape
```

```haskell
λ> map surface [Circle (Point 10 20) 30, Circle (Point 100 200) 300, Rectangle (Point 150 20) (Point 107 20)]
[188.49556,1884.9557,0.0]
```

The return type of `surface` is defined as a `Shape`. This is fine, because `Shape` is a type. We could NOT say that surface returns
a `Circle` or `Rectangle` because they are type constructors (functions) not types.

Notice that when defining a `Point`, we used the same name for the type and the value constructor. This is common practise when the
type only has one value constructor.

Also, we can export all the value constructors for a type from a module by using the `(...)` notation.

## Record Syntax
You can use record syntax to define compound data types: -

```haskell
data Person = Person { firstName :: String
                     , lastName :: String
                     , age :: Int
                     , height :: Int
                     , phoneNumber :: String
                     , flavor :: String
                     } deriving (Show)
names = map (reverse . firstName) people
  where people = [Person {firstName="Martin",lastName="Robson",age=55,height=185,phoneNumber="89898",flavor="Chilli"},
                  Person {firstName="Joe",lastName="Bloggs",age=25,height=190,phoneNumber="87987",flavor="curry"}] 
["nitraM","eoJ"]
```

When we define a compound data type like this, Haskell automatically defines functions that lookup values in the data type. In this case `firstName`, `lastName`, `age`, `height`, `phoneNumber` and `flavor`.

## Type Parameters
A value constructor can take some values parameters and then produce a new value. For instance, the `Person` constructor takes six values and produces a `Person` value. 
In a similar manner, 
**type constructors** can take types as parameters to produce new types. For example, the `Maybe` type is defined as: -

```haskell
data Maybe a = Nothing | Just a
```

The `a` here is the type parameter. And, because there is a type parameter involved, we call `Maybe` a type constructor.
Depending on what we want this data type to hold when it's not `Nothing`, this type constructor can end up producing a type of `Maybe Int`, `Maybe Char` or `Maybe Person`, etc.
No value can have a type of just `Maybe`, because `Maybe` is not a type it is a type constructor.
In order for this to be a real type that a value can be part of, it has to have all the type parameters filled in.

Note that the type of `Nothing` is `Maybe a`. Its type is polymorphic. If some function requires `Maybe Int` as a parameter, we can give it a `Nothing`. The `Maybe a` type
can act like a `Maybe Int` if it has to, just like `5` can act like an `Int` or a `Double`. Similarly the type of the empty list is `[a]`. 
An empty list can act like a list of anything. That's why we can do `[1,2,3] ++ []` and `['a','b','c'] ++ []`.

Another example of a parameterized type is `Map k v ` from `Data.Map`. The `k` is the type of the keys and the `v` is the type of the values.
If we were defining a mapping type, we could include a typeclass constraint in the *data* declaration:-

```haskell
data (Ord k) => Map k v = 
```

However, **it is a very strong convention in Haskell to never add type class constraints in data declarations**. This is because we would end up having to write more
class constraints even when we don't need them. For example, `Map` has a `toList` function that converts the map to a list of tuples. `toList` does not care about the ordering of the
type `k`, so has the type signature: -

```haskell
λ> :t Map.toList
Map.toList :: Map k a -> [(k, a)]
Prelude Map
```

If we included the type class constraint in the data declaration of `Map`, we would have to include it in the type declaration of `toList` also, if though it does not need it.

**It is very important to distinguish between the type constructor and the value constructor.** The part before the `=` is the type constructor and the constructors after it 
(possibly separated by `|`) are the value constructors.

Example Vector type: -

```haskell
data Vector a = Vector a a a deriving (Show)
data Vector a = Vector a a a deriving (Show)

vplus :: (Num t) => Vector t -> Vector t -> Vector t
(Vector i j z) `vplus` (Vector l m n) = Vector (i+l) (j+m) (z+n)

vmult :: (Num t) => Vector t -> t -> Vector t
(Vector i j z) `vmult`  m = Vector (i*m) (j*m) (z*m)
```
If you examine the type declaration for these functions, you'll see that they can operate only on vectors of the same type and the 
numbers involved must also be of the same type as contained in the vectors. Notice that we didn't put a `Num` class constraint in the data declaration
, because we would have to repeat it in the functions anyway.

Once again, it is very important to distinguish between the **type** constructor and the **value** constructor. When declaring a data type, the part before the 
= sign is the type constructor and the constructors after it (possibly separated by an `|`) are value constructors.
Giving a function of type `Vector t t t -> Vector t t t -> t` would be wrong, because we have to put types in the type declaration and the vector
type constructor takes only one parameter, whereas the value constructor takes three.
             
## Derived Instances
A **typeclass** is a sort of interface that defines a behaviour. A type can be made an **instance** of a typeclass if it supports that behaviour.
Haskell can automatically make our type an instance of any of the following typeclasses: `Eq`, `Ord`, `Enum`, `Bounded`, `Show`, `Read`. Haskell can
derive the behaviour of our types in these contexts if we use the *deriving* keyword when making our type.

```haskell
data Day = Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday
  deriving (Show, Eq, Ord, Read, Bounded, Enum)
``` 

```
λ> Monday < Tuesday
True
*Main 
λ> succ Monday
Tuesday
*Main 
λ> minBound :: Day
Monday
*Main 
λ> maxBound :: Day
Sunday
*Main 
λ> read "Saturday" :: Day
Saturday
*Main 
λ> show Saturday
"Saturday"
*Main 
λ>
```

## Type Synonyms
Type synonyms allow us to give types a specific name, for example `String` is defined in the standard library as a type synonym: -

```haskell
type String = [Char]
```

This uses the `type` keyword. Notice that we are not actually defining a new type (the `data` keyword does that), we are only defining a synonym for an existing type.

Type synonyms can also be parameterized. For example we can define an association list type like: -

```haskell
type AssocList k v = [(k,v)]
```

so a function that gets a value by key from an association list would have a signature of:-

```haskell
(Eq k) => k -> AssocList k v -> Maybe v
```

## Typeclasses
A quick recap on typeclasses: typeclasses are like interfaces. A typeclass define some behaviour (like comparing for equality, comparing for ordering, enumeration etc) and
then types that can behave in that way are made instances of that typeclass. The `Eq` class is defined in the standard prelude:

```haskell
class Eq a where
  (==) :: a -> a -> Bool
  (/=) :: a -> a -> Bool
  x == y = not (x /= y)
  x /= y = not (x == y)
```

we can use the `instance` keyword to define instances for our own types: -

```haskell
data TrafficLight = Red | Yellow | Green
instance Eq TrafficLight where
    Red == Red = True
    Green == Green = True
    Yellow == Yellow = True
    _ == _ = False
instance Show TrafficLight where
    show Red = "Red light"
    show Yellow = "Yellow light"
    show Green = "Green light"
instance (Eq m) => Eq (Maybe m) where
    Just x == Just y = x == y
    Nothing == Nothing = True
    _ == _ = False
```
Notice how we had to add a type constraint to the `Maybe` instance as we have to ensure that `m` is an instance of the `Eq` typeclass as well.


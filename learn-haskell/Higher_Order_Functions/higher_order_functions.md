# Higher Order Functions

## Curried Functions
Every function in Haskell only takes one parameter. If we supply less parameters than the function expects, we get a **partially applied** 
function back.

For example: - 

Standard function application

```haskell
λ> max 100 3
100
```

Partially applied function (the error is because functions are not instances of the `Show` typeclass).
```haskell
λ> max 100

<interactive>:7:1: error:
    • No instance for (Show (Integer -> Integer))
        arising from a use of ‘print’
        (maybe you haven't applied a function to enough arguments?)
    • In a stmt of an interactive GHCi command: print it
``` 

Try again
```haskell
λ> let biggerThan100 = max 100
Prelude
λ> :t biggerThan100
biggerThan100 :: (Ord a, Num a) => a -> a
Prelude
λ> biggerThan100 1
100
Prelude
λ> biggerThan100 200
200
Prelude
λ>
```

## Higher Order Functions 
A higher order function is a function that can take function(s) as parameters and/or return function(s) as return values. For example we can write a function that takes
a function and applies it twice: -

```haskell
applyTwice :: (a -> a) -> a -> a
applyTwice f x = f (f x)
```
e.g.
```haskell
λ> applyTwice (+10) 100
120
*Main
λ> applyTwice reverse [1,2,3]
[1,2,3]
*Main
```

First of all, notice the type declaration. Before we did not need parentheses because `->` is naturally right-associative. However, here they are mandatory. 
They indicate that the first parameter is a function that takes something and returns the same thing. 
The second parameter is something of that type also and the return value is also of the same type. 
We could read this type declaration in a curried way, but to save ourselves a headache, we can just say that this function takes two parameters (one of which is a function) and returns one thing. 

In functional programming, we tend to use higher order functions to abstract away common patterns. For example the `zipWith` function below can examine two lists
in pairs and do something with those pairs (the higher order function).

```haskell
zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' _ [] _ = []
zipWith' _ _ [] = []
zipWith' f (x:xs) (y:ys) = f x y : zipWith' f xs ys
```

```haskell
λ> zipWith' (++) ["fred","g","a"] ["Hello", "Dog", "Cat"]
["fredHello","gDog","aCat"]
*Main
λ> zipWith' (zipWith' (*)) [[1,2,3],[4,5,6],[7,8,9]] [[1,2,3],[1,2,3],[1,2,3]]
[[1,4,9],[4,10,18],[7,16,27]]
*Main
λ>
```

## Maps and Filters
`map` takes a function and a list and applies that function to every element of the list, producing a new list. It is defined like: -

```haskell
map' :: (a -> b) -> [a] -> [b]
map' _ [] = []
map' f (x:xs) = f x : map' f xs
```

```
λ> map' (*10) [1,2,3,4,5]
[10,20,30,40,50]
```

A filter takes a predicate and a list and removes anything from the list that does not pass the predicate: -

```haskell
filter' :: (a -> Bool) -> [a] -> [a]
filter' _ [] = []
filter' p (x:xs)
    | p x        = x : filter' p xs
```

```
λ> filter (>5) [1,2..10]
[6,7,8,9,10]
```


The same functionality can be expressed using list comprehensions: -

```haskell
λ> [ x*10 | x <- [1,2..5] ]
[10,20,30,40,50]
```

```haskell
λ> filter (>5) [1,2..10]
[6,7,8,9,10]
```

### Mapping partially applied functions
Using `map` we can also do stuff like `map (+) [0..]`, if not for any other reason than to illustrate how 
currying works and how (partitally applied) functions are real values that you can pass around to other
functions or put into lists (you just can't turn them into strings).

```haskell
λ> let a = map (+) [1,2,3,4,5]
*Main
λ> :t a
a :: Num a => [a -> a]
*Main
λ> (a !! 2 ) 5
8
```

## Lambdas
Lambdas are anonymous functions that are used because we need some functions only once. Normally we
make a lambda with the sole purpose of passing it to a higher order function.

```haskell
λ> zipWith' (\a b -> (a * 30 + 3) / b) [5,4..1] [1,2..5]
[153.0,61.5,31.0,15.75,6.6]
```

## Folds
A fold takes a binary function, a starting value (the accumulator) and a list to fold up.

### Example `foldl`

```haskell
λ> :t foldl
foldl :: Foldable t => (b -> a -> b) -> b -> t a -> b
```

so, a list sum function could be written as: -

```haskell
λ> foldl (\z a -> z + a) 0 [1,2,3]
6
```

but this can be simplified to: -

```haskell
λ> foldl (+) 0 [1,2,3]
6
```

because of currying, we can write a function `sum'` like:-

```haskell
sum' :: (Num a) => [a] -> a
sum' = foldl (+) 0
```

Notice that we did not need to provide the third argument (list) to foldl. This is because the `sum'` function returns a function
that accepts a list. In general in Haskell when you have a function like `foo a = bar b a` you can re-write it as `foo = bar b`
because of currying.

Folds can be used to implement any function where you traverse a list once, element by element and then return something based on
that. Whenever you want to traverse a list to return something, chances are you want a fold.

## Function Application with `$`
Function application function is defined as: -

```haskell
($) :: (a -> b) -> a -> b
f $ x = f x
```

Function application with a space is left-associative whereas function application with a `$` is right-associative.
It can be used as a convenience function so we don't have to write so many parentheses: -

```haskell
sum (filter (> 10) (map (*2) [2..10]))
```

Can be written as: -

```haskell
sum $ filter (> 10) $ map (*2) [2..10]
```

But, apart from getting rid of parentheses, `$` means that function application can be treated just like another function. So, we can map function application over a list of functions: -

```haskell
map' ($ 3) [(+1),(+2),(min 10), (max 10)]
[4,5,3,10]
```

## Function Composition
In mathematics, function composition is defined as, `(f . g)(x) = f(g(x))`, meaning that composing two functions produces a new function that, when called with a parameter, say `x`, is equivalent of calling *g* with the 
parameter *x* and then calling *f* with that result.

In Haskell we do function composition with the `.` function

```haskell
(.) :: (b -> c) -> (a -> b) -> a -> c
f . g  = \x -> f (g x)
```

One of the uses of function composition is to make functions on the fly to pass to other functions. Lambdas can also be used, but function composition is often clearer: -

```haskell
map (\x -> negate $ abs x) [5,-3,-6,7,-3,2,-19,24]
[-5,-3,-6,-7,-3,-2,-19,-24]
```

```haskell
map (negate . abs ) [5,-3,-6,7,-3,2,-19,24]
[-5,-3,-6,-7,-3,-2,-19,-24]
```

Function composition is right associative, so `f (g (z x))` is the same as `(f . g . z) x`, so 

```haskell
map (\xs -> negate (sum (tail xs))) [[1..5],[3..6],[1..7]]
[-14,-15,-27]
```

Can be written as: -

```haskell
λ> map (negate . sum . tail) [[1..5],[3..6],[1..7]]
[-14,-15,-27]
```

But what about functions that take several parameters? If we want to use these in function composition, we have to partially apply them so that each function takes one parameter, so

```haskell
sum (replicate 5 (max 6.7 8.9))
44.5
```

Can be written as: -

```haskell
sum . replicate 5 . max 6.7 $ 8.9
44.5
```

If you want to rewrite an expression with a lot of parentheses by using function composition, you can start by putting the last parameter of the innermost function after an `$` and then just composing the other function calls,
writing them without their last parameter and just putting dots between them: -

### Example 1

```haskell
replicate 100 (product (map (*3) (zipWith max [1..5] [4..8])))
```

```haskell
replicate 100 . product . map (*3) . zipWith max [1..5] $ [4..8]
```

If the expression ends with three parentheses, chances are if you translate it into function composition, it will have three composition operators.

### Example 2

```haskell
reverseWords :: String -> String
reverseWords s = unwords (map reverse (words s))
```

becomes

```haskell
reverseWords' :: String -> String
reverseWords' s = unwords . map reverse . words $ s

```

which (using currying) can be simplified to: -

```haskell
reverseWords' :: String -> String
reverseWords' s = unwords . map reverse . words 
```

### Point free style
Another common use of function composition is defining functions in the so called *point free style* (also called *pointless* style), for example the `sum` function: -


### Example 1
```haskell
sum' :: (Num a) => [a] -> a
sum' xs = foldl (+) 0 xs
```
Because of currying, we can omit the `xs` on both sides, `foldl (+) 0` create a function that takes a list: -

```haskell
sum'' :: (Num a) => [a] -> a
sum'' = foldl (+) 0
```

### Example 2

```haskell
--
-- Pointless composition
--
fn :: (RealFrac a, Integral b, Floating a) => a -> b
fn x = ceiling (negate (tan (cos (max 50 x))))
--
-- Using function composition rule
-- (replace all open brackets with '.' and the innermost function second argument with '$'
fn' :: (RealFrac a, Integral b, Floating a) => a -> b
fn' x = ceiling . negate . tan . cos . max 50 $ x
--
-- Because x is mentioned on both sides, we can use currying to simplify further.
-- This is called pointless composition.
-- becomes: -
fn'' :: (RealFrac a, Integral b, Floating a) => a -> b
fn'' = ceiling . negate . tan . cos . max 50
--
--
main = do
    print $ fn 34.4
    print $ fn' 34.4
    print $ fn'' 34.4
```

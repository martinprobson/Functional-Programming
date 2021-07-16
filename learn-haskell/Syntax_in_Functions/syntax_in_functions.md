# Syntax in Functions

## Pattern Matching
Pattern matching consists of specifying patterns to which some data should conform and then checking to see if it does and deconstructing the data according to those patterns.
When defining functions, you can define separate function bodies for difference patterns.

Note that the `(x:xs)` pattern is used a lot when matching against lists. But patterns that have `:` in them only match against lists of length 1 or more.

Note that to match a tuple, the `(a,b)` pattern can be used.

### "as" patterns
As patterns are a way of breaking up something according to a pattern and binding it to names whilst still keeping a reference to the whole thing: -

```haskell
capital :: String -> String
capital all@(x:xs) = "The first letter of " ++ all ++ " is " ++ [x]
```

## Guards
Whereas patterns are a way of making sure a value conforms to some form and de-constructing it, guards are a way of testing whether some property of a value (or several of them) are true or false.

```haskell
bmitell :: (RealFloat a) => a -> a -> String
bmitell w h
  | w / h ^ 2 <= 18.5 = "You're underweight"
  | w / h ^ 2 <= 25.0 = "Normal"
  | w / h ^ 2 <= 30.0 = "Fat"
``` 

Guards are indicated by pipes that follow a function's name and parameters. Usually they are indented a bit to the right and
lined up. A guard is basically a boolean expression, if it evaluates to true, then the corresponding function body is used. If it
evaluates to false, checking drops through to the next guard and so on.

Many times the last guard is `otherwise`. `otherwise` is defined simply as `otherwise = True` and catches everything. This is very
similar to patterns, only they check if the input matches a pattern but guards check for boolean conditions. If all the guards
of a function evaluate to `False` (and we have not provided an `otherwise` catch-all guard), evaluation falls through to the next pattern.
That's how guards and patterns play nicely together. If no suitable guards or patterns are found, then an error is thrown.

## Where
You can also add a `where` statement to prevent repeats.

```haskell
 -- Second version with a where statement
bmitell' :: (RealFloat a) => a -> a -> String
bmitell' w h
  | bmi <= thin = "You're underweight"
  | bmi <= normal = "Normal"
  | bmi <= fat = "Fat"
  | otherwise = "very fat"
  where bmi = w / h ^ 2
        thin = 18.5
        normal = 25.0
        fat = 30.0
```	

Where bindings can also be nested. It is a common idiom to make a function and define some helper function in its where clause
and then to give those functions helper functions as well, each with its own where clause.

### Example - Nested functions using where


```haskell
main = do
    putStrLn "Hello what is your name?"
    name <- getLine
    putStrLn $ "This is your name reversed: " ++ reverse' name
    where 
        reverse' :: [a] -> [a]
        reverse' l = foldl (flip' (:)) [] l
            where flip' f a b = f b a
```

## Let Bindings
Let bindings are very similar to where bindings. Where bindings are a syntactic construct that let you bind variables at the end of a function and the whole function can 
see them, including all the guards. Let bindings let you bind to variables anywhere and are expressions themselves, but are very local. so they don't span across guards.

```haskell
--
-- Surface area of a cylinder with where statement
--
cylinder :: (RealFloat a) => a -> a -> a
cylinder r h = sideArea + 2 * topArea
  where sideArea = 2 * pi * r * h
        topArea = pi * r^2
--
-- Surface area of a cylinder with let statement
--
cylinder' :: (RealFloat a) => a -> a -> a
cylinder' r h = 
  let sideArea = 2 * pi * r * h
      topArea = pi * r^2
   in sideArea +2 * topArea
```

The general form of the `let` statement is : -

```
let <bindings> in <expression>
```

The names you define in the let part are accessible to the expression in the in part. The main difference between `where` and `let` is that `where` is just a syntactic construct but `let` binding are expressions. So you can
do something like: -

```haskell
4 * (let a = 9 in a + 1) + 2
42
```	

You can also use let to introduce functions in a local scope: -

```haskell
λ> [ let sqr x = x*x in (sqr 2, sqr 4, sqr 8) ]
[(4,16,64)]
```


## Case Expressions
Like many other languages, Haskell also has a `case` expression of the form: -

```
case <expression> of <pattern> -> <result>
                     <pattern> -> <result>
```

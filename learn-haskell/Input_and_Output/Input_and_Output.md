# Input and Output

## Introduction
Haskell has a clever system for dealing with functions that have side-effects that neatly separates the part of our program
that is pure, and the part of the program that is impure.

For example, to output a string we can use the `putStrLn` function: -

```haskell
main :: IO ()
main = putStrLn "Hello world!"
```
```haskell
:t putStrLn
putStrLn :: String -> IO ()
```

`putStrLn` takes a string and returns an **I/O action** that has a result type of `()`.

**An IO is a type of Monad**. It acts as a container for an I/O operation that may fail. The `do` notation can be used to chain I/O
operations together: -

### Example 1
```haskell
main = do
  putStrLn "What is your name? "
  name <- getLine
  putStrLn ("Hello " ++ name)
```

This is syntactic sugar for a series of bind (flatMap) operations, so: -

```haskell
a >> b >> c >> d
```

sequences operations, ignoring the result of the previous operation: -

```haskell
do a
   b
   c
   d
```

whereas 

```haskell
return 1 >>= (\r1 -> (return 2 >>= (\r2 -> (pure 3 >>= (\r3 -> return ((r1, r2, r3)))))))
```

does not ignore the result of the previous operation and is equivalent to: -

```haskell
do
  r1 <- return 1
  r2 <- return 2
  r3 <- return 3
  return (r1,r2,r3)
```


```haskell
putStrLn "What is your name?" >> ( getLine >>= (\name -> putStrLn ("Hello " ++ name)))
```

### Example 2 - getLine

```haskell
getLine' :: IO String
getLine' = do 
  c <- getChar
  if c == '\n'
     then return ""
     else do
       l <- getLine'
       return (c:l)
``` 

```haskell
getLine'' :: IO String
getLine'' = getChar >>= (\c -> if c == '\n' then return "" else getLine'' >>= (\l -> return (c:l)))
```

### Example 3

```haskell

main = main''

-- main using do syntax sugar
main' :: IO ()
main' = do
    putStrLn "Hello what is your name?"
    name <- getLine
    putStrLn $ "This is your name reversed: " ++ reverse'' name

-- main (de-sugared)
main'' :: IO ()
main'' = 
  putStrLn "Hello what is your name?" >> getLine >>= (\name -> putStrLn $ "This is your name reversed: " ++ reverse'' name)

-- Different implementations of reverse function
reverse'' :: [a] -> [a]
reverse'' [] = []
reverse'' (x:xs) = reverse'' xs  ++ [x]

reverse''' :: [a] -> [a]
reverse''' l = foldr (\a b -> b ++ [a]) [] l

reverse'''' :: [a] -> [a]
reverse'''' l = foldl (flip' (:)) [] l

-- Implementation of flip function (used above)
flip' :: (a -> b -> c) -> b -> a -> c
flip' f a b = f b a

```

### Example 4

```haskell
main = do
    contents <- readFile "example4.hs"
    putStrLn $ reverse' contents

reverse' :: [a] -> [a]
reverse' l = foldl (flip' (:)) [] l

-- Implementation of flip function (used above)
flip' :: (a -> b -> c) -> b -> a -> c
flip' f a b = f b a

```

## Randomness
The `System.Random` package can be used to generate randomness in a referentially transparent manner. One of the functions it exports is called `random` which has the following type signature: -

```haskell
random :: (RandomGen g, Random a) => g -> (a,g)
```

where `RandomGen` is a typeclass for types that can act as a source of randomness, and the `Random` typeclass is for types that can take on random values.

The `System.Random` module exports a type called `StdGen` that is an instance of the `RandomGen` typeclass. You can either make a `StdGen` manually or we can tell the system
to give supply us with one.

## Exceptions
Generally it is better to use types like `Maybe` or `Either` to represent error conditions that can be returned from functions. Pure code can throw exceptions, (such as divide by zero or the head of an
empty list) but exceptions can only be caught in the I/O part of out code (when we are inside a `do` block that goes into `main`). This is because you do not know when (or if) anything will be evaluated 
in pure code, because it is lazy and it does not have a well defined order of execution, wheras I/O code does.

```haskell
import System.Environment
import System.IO
import System.IO.Error
import Control.Exception

main = catch toTry handler

toTry :: IO () 
toTry = do
    (filename:_) <- getArgs
    contents <- readFile filename
    putStrLn $ "The file has " ++ show (length (lines contents)) ++ " lines!"

handler :: IOError -> IO ()
handler e 
  | isDoesNotExistError e = putStrLn "The file does not exist"
  | otherwise = ioError e
```

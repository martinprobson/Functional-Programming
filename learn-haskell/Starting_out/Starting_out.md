# Starting Out

## Boolean algebra

```haskell
λ> True && False
False
Prelude
λ> True && True
True
Prelude
λ> False || True
True
Prelude
λ> not False
True
Prelude
λ> not (True && True)
False
Prelude
```

## Equality

```haskell
λ> 5 == 5
True
Prelude
λ> 1 == 0
False
Prelude
λ> 5 /= 5
False
Prelude
λ> 5 /= 4
True
Prelude
λ> "hello" == "hello"
True
Prelude
λ>
```

## Functions
In Haskell, functions are called by writing the function name, a space and then the parameters, separated by spaces, e.g: -

All functions **must** begin with a lowercase letter.

```haskell
succ 8
```

### Infix Functions
A function can be used as an infix function by surrounding it with backticks: -

```haskell
λ> elem 1 [3,2,1]
True
*Main
λ> 1 `elem` [3,2,1]
True
```

Note that if a function is comprised only of special characters, it's considered an infix function by default. If we want to examine its type, pass it
to another function or call it as a prefix function, we have to surround it in parentheses.

#### Infix Function Example
For example, we could factor out the common code for the `maximum'` and `minimum'` functions for a list by passing the comparator into a helper function: -

```haskell
maximum' :: (Ord a) => [a] -> a
maximum' l = minmax l (>)

minimum' :: (Ord a) => [a] -> a
minimum' l = minmax l (<)

minmax :: (Ord a) => [a] -> (a -> a -> Bool) -> a
minmax  [] _ = error "Min of empty list!"
minmax  [x] _ = x
minmax  (x:xs) f
            | f  x t = x
            | otherwise = t
            where t = minmax xs f
```

### Function Application Precedence
Function application in Haskell has very high precedence, this means that these two statements are equivalent:-

```haskell
λ> succ 1 * min 2 10 + 1
5
*Main
λ> (succ 1) * (min 2 10) + 1
5
```

## If statement
An `if` statement in Haskell is an expression (i.e. it returns a value). The `else` part is mandatory.

```haskell
isEven :: (Integral a) => a -> Bool
isEven x = if (x `mod` 2 == 0) 
              then True
              else False
```

## Lists
In Haskell, lists are an homogenous data structure. They are denoted by square brackets and the values in the list are separated by commas.

### `++` Operator
The ``++` operator joins two lists together: -

```haskell
λ> [1..10] ++ [1..10]
[1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10]
*Main
λ>
```

The `++` operator is not efficient, as Haskell has to walk through the list on the left hand side to append.

### `:` Operator
The `:` (Cons) operator adds a single element to the head of a list.

```haskell
λ> 1 : [2..10]
[1,2,3,4,5,6,7,8,9,10]
*Main
```

### `!!` Operator
Use `!!` to access a list by index (starting from zero).

```haskell
λ> "Martin" !! 2
'r'
λ> "Martin" !! 20
*** Exception: Prelude.!!: index too large
```

### List Functions

| Name    | Description                                                                      |
| head    | First element of a list, error if list is empty                                  |
| tail    | All elements of a list, except the first. Error if the list is empty             |
| last    | Last element of a list, error if the list is empty                               |
| init    | Returns everything except the last element of a list, error if the list is empty |
| length  | Return the length of a list                                                      |
| null    | Check if a list is empty                                                         |
| take    | Take n elements from the front of a list                                         |
| drop    | Drop n elements from the front of a list                                         |
| maximum | Find the maximum value in a list                                                 |
| minimum | Find the minimum value in a list                                                 |
| sum     | Sum the values in a list                                                         |
| product | Return the product of a list                                                     |
| elem    | Return true if an element is in the list, false otherwise                        |

We can re-implement these list functions using pattern matching and recursion: -

```haskell
--
-- re-implement some of the basic list functions supported by Haskell
--
head' :: [a] -> a
head' []        = error "empty list"
head' (x:_)     = x

tail' :: [a] -> [a]
tail' []        = error "empty list"
tail' (_:xs)    = xs

last' :: [a] -> a
last' []        = error "empty list"
last' [x]       = x
last' (_:xs)    = last' xs

init' :: [a] -> [a]
init' []        = error "empty list"
init' [x]       = []
init' (x:xs)    = x : init' xs

length' :: [a] -> Int
length' []      = 0
length' (x:xs)  = 1 + length' xs

length'' :: [a] -> Int
length'' xs = sum [ 1 | _ <- xs]

-- TODO Can this be simplified?
null' :: [a] -> Bool
null' []        = True
null' [x]       = False
null' (x:xs)    = False

take' :: (Integral a) => a -> [b] -> [b]
take' n (x:xs)
  | n <= 0 =  []
  | otherwise = x : take' (n-1) xs

drop' :: (Integral a) => a -> [b] -> [b]
drop' _ [] = []
drop' n (x:xs)
  | n <= 0 =  x:xs
  | otherwise = drop' (n-1) xs

maximum' :: (Ord a) => [a] -> a
maximum' l = minmax l (max)

minimum' :: (Ord a) => [a] -> a
minimum' l = minmax l (min)

minmax :: (Ord a) => [a] -> (a -> a -> a) -> a
minmax  [] _ = error "Min of empty list!"
minmax  [x] _  = x
minmax  (x:xs) f = f x (minmax xs f)

sum' :: (Num a) => [a] -> a
sum' [] = 0
sum' (x:xs) = x + sum' xs

product' :: (Num a) => [a] -> a
product' [] = 1
product' (x:xs) = x * product' xs

elem' :: (Eq a) => a -> [a] -> Bool
elem' _ [] = False
elem' a (x:xs)
          | a == x = True
          | otherwise = elem' a xs
```

### Ranges

| Example                 | Description                  |
| [1..20]                 | All numbers 1 to 20 inclusve |
| ['a'..'z']              | a to z                       |
| [2,4..20]               | [2,4,6,8,10,12,14,16,18,20]  |
| [20,19..1]              | 20 to 1 inclusive            |
| take 10 [1,2..]         | Infinite range               |
| take 10 [10,20..]       | Infinite range 10,20,30...   |
| take 10 $ cycle [1,2,3] | Infinite cycle of [1,2,3]    |
| take 10 $ repeat "M"    | Infinite list of "M"         |

### List Comprehension
List comprehensions behave in a similar way to [set builder notation](https://en.wikipedia.org/wiki/Set-builder_notation) in mathematics. In Haskell they consists of three parts: -

```
[ <output_function> | <Set(s) to draw from> , <predicates> ]
```

#### Examples

The first 10 even natural numbers:-

```haskell
λ> [x*2 | x <- [1..10]]
[2,4,6,8,10,12,14,16,18,20]
```

All possible products of `[2,5,10]` and `[8,10,11]` that are greater than 50

```haskell
λ> [ x*y | x <- [2,5,10], y <- [8,10,11], x*y > 50]
[55,80,100,110]
```

Only uppercase letters in a string

```haskell
λ> uppercase s = [ c | c <- s, c `elem` ['A'..'Z']]
Prelude
λ> uppercase "Fred Bloggs"
"FB"
```

The `length'` function in the previous section could be re-written as a list comprehension: -

```haskell
length'' :: [a] -> Int
length'' xs = sum [ 1 | _ <- xs]
```

Comprehensions also work against nested lists: -

```haskell
λ> let xxs = [[1,3,5,2,3,1,2,4,5],[1,2,3,4,5,6,7,8,9],[1,2,4,2,1,6,3,1,3,2,3,6]]
*Main
λ> [ [ x | x <- xs, even x ] | xs <- xxs]
[[2,2,4],[2,4,6,8],[2,4,2,6,2,6]]
```

## Tuples
Tuples are of a fixed size and type and are denoted by parentheses. The functions `fst` and `snd` work on tuple pairs and take the first and second element respectively. The `zip` function takes two lists and zips them
together into one list by joining the matching elements in pairs: -

```haskell
λ> zip [1,2..] ['A'..'Z']
[(1,'A'),(2,'B'),(3,'C'),(4,'D'),(5,'E'),(6,'F'),(7,'G'),(8,'H'),(9,'I'),(10,'J'),(11,'K'),(12,'L'),(13,'M'),(14,'N'),(15,'O'),(16,'P'),(17,'Q'),(18,'R'),(19,'S'),(20,'T'),(21,'U'),(22,'V'),(23,'W'),(24,'X'),(25,'Y'),(26,'Z')]
```


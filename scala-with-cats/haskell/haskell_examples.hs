import Data.List (span)

isZero :: (Integral a) => a  -> Bool
isZero 0 = True
isZero x = False

double :: (Integral a) => a -> a 
double n = 2 * n

doubleSmallNumber :: (Integral a) => a -> a
doubleSmallNumber n
 | n < 100 = double n
 | otherwise  = n

doubleMe x = x + x
doubleUs :: (Integral a) => a -> a -> a
doubleUs x y = x + x + y + y
doubleUs' x y = doubleMe x + doubleMe y

boomBangs :: (Integral a) => [a] -> [String]
boomBangs xs = [ if x < 10 then "Boom!" else "Bang!" | x <-xs, odd x ]

add :: (Num a) => a -> a -> a
add x y = x + y

--
-- define length with a list comprehension
--
length' :: [a] -> Int
length' xs = sum [ 1 | _ <- xs ]

--
-- Which right triangle that has integers for all sides and all sides equal to or smaller than 10
-- has a perimeter of 24?
--
--
triangles = [ (a,b,c) | c <- [1..10], b <- [1..c], a <- [1..b], a^2 + b^2 == c^2, a + b + c == 24 ]

removeNonUpperCase :: [Char] -> [Char]
removeNonUpperCase st = [c | c <- st, c `elem`['A'..'Z'] ]
 
removeSpace:: [Char] -> [Char]
removeSpace st = [c | c <- st, c /= ' ' ]

firstWord:: [Char] -> [Char]
firstWord st = takeWhile' (/= ' ') st

lucky :: (Integral a) => a -> String
lucky 7 = "Lucky number 7!"
lucky _ = "Sorry you lose!"

head' :: [a] -> a
head' [] = error "empty list!"
head' (x:_)  = x

tail' :: [a] -> [a]
tail' [] = error "empty list!"
tail' (_:xs)  = xs

--
-- fold left and fold right
--
foldl' :: (b -> a -> b) -> b -> [a] ->  b
foldl' _ b [] = b
foldl' f b (x:xs) = foldl' f (f b x) xs

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr' _ b [] = b
foldr' f b (x:xs) = f x (foldr' f b xs)

reverse' :: [a] -> [a]
reverse' l = foldl' (\acc x -> x : acc) [] l

length'' :: [a] -> Int
length'' [] = 0
length'' (_:xs) = 1 + length xs

sum' :: (Num a) => [a] -> a
sum' [] = 0
sum' (x:xs) = x + sum' xs

fibonacci :: (Integral x) => x -> x
fibonacci x
  | x == 0 = 0
  | x == 1 = 1
  | otherwise = fibonacci (x -1) + fibonacci (x -2)

--
-- Sum of all odd squares less than n
--
sumSquaresLess:: (Integral n) => n -> n
sumSquaresLess n
  | n <= 0 = 0
  | otherwise = sum' $ takeWhile' (<n) $ filter' (odd) (map' (^2) [1..])

maximum' :: (Ord a) => [a] -> a
maximum'  [] = error "Max of empty list!"
maximum'  [x] = x
maximum'  (x:xs)
            | x > maxTail = x
            | otherwise = maxTail
            where maxTail = maximum' xs

replicate' :: (Num i, Ord i) => i -> a -> [a]
replicate' n x
  | n == 1 = [x]
  | otherwise = x : (replicate' (n - 1) x)

cycle' :: (Num i, Ord i) => i -> [a] -> [a]
cycle' n x
  | n <= 0 = x
  | otherwise = x ++ (cycle' (n - 1) x)

quicksort' :: (Ord a) => [a] -> [a]
quicksort' [] = []
quicksort' (x:xs) = quicksort' (filter (<=x) xs)
                   ++ [x] ++
                   quicksort' (filter (>x) xs)


add' :: (Num a) => a -> a -> a
add' a b = a + b

sub' :: (Num a) => a -> a -> a
sub' a b = a - b

--
-- Apply the same function twice
--
applyTwice' :: (a -> a) -> a -> a
applyTwice' f x = f (f x )

zip' :: [a] -> [b] -> [(a,b)]
zip' _ [] = []
zip' [] _ = []
zip' (x:xs) (y:ys) = (x,y) : zip' xs ys


zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' _ [] _  = []
zipWith' _ _ []  = []
zipWith' f (x:xs) (y:ys) = f x y : zipWith' f xs ys

flip' :: (a -> b -> c) -> (b -> a -> c)
flip' f x y = f y x

map' :: (a -> b) -> [a] -> [b]
map' _ [] = []
map' f (x:xs) = (f x) : map' f xs

filter' :: (a -> Bool) -> [a] -> [a]
filter' _ [] = []
filter' p (x:xs) = if  p x then 
                           x : filter' p xs
                   else
                           filter' p xs

takeWhile' :: (a -> Bool) -> [a] -> [a]
takeWhile' _ [] = []
takeWhile' p (x:xs) = if p x then
                             x : takeWhile' p xs
                      else
                             []

take' :: (Integral a) => a -> [b] -> [b]
take' n (x:xs)
  | n <= 0 =  []
  | otherwise = x : take' (n-1) xs

--
-- Making our own Types and Typeclasses
--
data Shape = Circle Point Float | Rectangle Float Float Float Float deriving (Show)
data Point = Point Float Float deriving (Show)

-- Scala's Option type
data Option a = None | Some a deriving (Show, Eq)

isEmpty :: Option a -> Bool
isEmpty None = True
isEmpty _ = False

get' :: Option a -> a
get' None = error "empty"
get' (Some a) = a

--
-- Given a default value and a function, apply the function to the value in Option or return the default value
--
getOrDefault' :: b -> (a -> b) -> Option a -> b
getOrDefault' n _ None = n
getOrDefault' _ f (Some x) = f x

--getOrElse' :: b -> Option a -> b
--getOrElse' n x = getOrDefault' n (id) x

data List a = Empty | Cons a (List a) deriving (Show,  Eq)

class Functor' f where
         fmap' :: (a -> b) -> f a -> f b

instance Functor' [] where
        fmap' = map

instance Functor' Option where
        fmap' f (Some x) = Some (f x)
        fmap' f None     = None

isSpace' :: Char -> Bool
isSpace' ' ' = True
isSpace' _ = False
--
-- Taken from Prelude (how does this work?)
--
words' :: String -> [String]
words' s =  case dropWhile isSpace' s of
              "" -> []
              s' -> w : words' s''
                    where (w, s'') = break isSpace' s'

--
--

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



double :: (Integral a) => a -> a 
double n = 2 * n
doubleList :: (Integral a) => [a] -> [a]
doubleList [] = []
doubleList (x:xs) = double x : doubleList xs

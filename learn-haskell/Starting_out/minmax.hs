maximum' :: (Ord a) => [a] -> a
maximum' l = minmax l (>)

minimum' :: (Ord a) => [a] -> a
minimum' l = minmax l (<)

minmax :: (Ord a) => [a] -> (a -> a -> Bool) -> a
minmax [] _ = error "Min of empty list!"
minmax [x] _ = x
minmax (x:xs) f
    | f x t = x
    | otherwise = t
    where t = minmax xs f

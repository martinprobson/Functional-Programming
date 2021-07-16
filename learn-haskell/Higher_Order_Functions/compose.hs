--(.) :: (b -> c) -> (a -> b) -> a -> c
--f . g  = \x -> f (g x)

add1 :: (Num a) => a -> a 
add1 n = n + 1
add10 :: (Num a) => a -> a
add10 n = n + 10

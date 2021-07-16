pyth :: Num a => a -> a -> a
pyth a b = a * a + b * b
test :: (Num a, Ord a) => a -> a
test = sum . replicate 5 . max 10 . min 5
test' :: (Num a, Ord a) => a -> a
test' = sum . replicate 5 . max 10 . min 5
main = do
    putStrLn "Hello world!"
    print "Hello World!"
    print $ pyth  (3 * 2)  (pyth 8 (-1))
    print (sqrt (pyth 3 ((-1) - 3)))
    print . sqrt . pyth 3 $ (-1) - 3
    print . sqrt . pyth 3 $ -1 - 3
    print $ sum (replicate 5 (max 10 (min 5 5000)))
    print . sum . replicate 5 . max 10 . min 5 $ 5000
    print $ test 5000
    print $ test' 5000




main = do
    contents <- readFile "example4.hs"
    putStrLn $ reverse' contents

reverse' :: [a] -> [a]
reverse' l = foldl (flip' (:)) [] l

-- Implementation of flip function (used above)
flip' :: (a -> b -> c) -> b -> a -> c
flip' f a b = f b a

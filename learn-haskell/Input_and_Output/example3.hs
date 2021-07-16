main :: IO ()
main = do
    putStrLn "Hello what is your name?"
    name <- getLine
    putStrLn $ "This is your name reversed: " ++ reverse' name
    where 
        reverse' :: [a] -> [a]
        reverse' l = foldl (flip' (:)) [] l
            where flip' f a b = f b a



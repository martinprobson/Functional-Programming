headInt :: [Int] -> Int
headInt [] = error "error"
headInt (x:xs) = x

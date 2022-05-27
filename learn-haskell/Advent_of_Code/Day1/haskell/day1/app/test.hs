
check :: (Num a, Eq a, Show a) => [a] -> String
check [] = "Empty"
check [x] = "One value " ++ show x
check (x:y:[]) = "Two values"
check (x:y:z:[]) = "Three values"
check (x:y:z:xs) = "More than three values"


check []
c


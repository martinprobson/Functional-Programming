
threes :: (Num a, Show a) => [a] -> [[a]]
threes [] = []
threes (x:xs) = map (x:) (pair xs) ++ (threes xs)

threes' :: (Num a, Show a) => [a] -> [a] -> [[a]]
threes' _ [] = []
threes' [x,y] [z] = [[x,y,z]]
threes' all@[x,y] (z:zs) = [x,y,z] : threes' all zs

pair :: (Num a) => [a] -> [[a]]
pair [] = []
pair (x:xs) = pair' x xs ++ pair xs
  where 
    pair' :: Num a => a -> [a] -> [[a]]
    pair' _ [] = []
    pair' n [x] = [[n,x]]
    pair' n (x:xs) = [n,x] : pair' n xs

main = do
  putStrLn . show $ threes []
  putStrLn . show $ threes [1]
  putStrLn . show $ threes [1,2]
  putStrLn . show $ threes [1,2,3]
  putStrLn . show $ threes [1,2,3,4]


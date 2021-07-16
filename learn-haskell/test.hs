f1 :: String
f1 = "Martin"
f2 :: (String -> String)
f2 = reverse
--
data Result = Result String deriving (Show)
getResult :: Result -> String
getResult (Result x) = x
func1 :: Result -> Either String Result 
func1 (Result s) = Right $ Result $ s ++ " " ++ "r1"
f3 :: (Result -> Either String Result)
f3 = func1
genFunc :: String -> (Result -> Either String Result)
genFunc n = (\r -> Right $ Result $ (getResult r) ++ " " ++ n)

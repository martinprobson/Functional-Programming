--
-- Example functional way of executing functions in sequence
--
--
-- This is the result passed from one function to another (could contain anything)
data Result = Result String deriving (Show)
getResult :: Result -> String
getResult (Result x) = x

-- Each Step has a name (tag) and a function that takes a Result and returns an Either.
data Step = Step {name :: String, func :: (Result -> Either String Result) }

genFunc :: String -> (Result -> Either String Result)
genFunc n = (\r -> Right $ Result $ (getResult r) ++ " " ++ "r" ++ n)
-- Some simple functions
func1 :: Result -> Either String Result 
func1 (Result s) = Right $ Result $ s ++ " " ++ "r1"
func2 :: Result -> Either String Result 
func2 (Result s) = Right $ Result $ s ++ " " ++ "r2"
func3 :: Result -> Either String Result 
func3 (Result s) = Right $ Result $ s ++ " " ++ "r3"
func4 :: Result -> Either String Result 
func4 (Result s) = Right $ Result $ s ++ " " ++ "r4"
funcFail :: Result -> Either String Result
funcFail (Result s) = Left $ s ++ " " ++ "Whoops!"

-- This will run each function in the sequence, passing results between each function
-- it will skip remaining functions if an exception (Failure) is returned
run :: [Step] -> Either String Result
run s = foldl (\b a -> b >>= (func a)) (Right (Result "OK")) s

-- This will run each function in the sequence, passing results between each function
-- it will skip remaining functions if an exception (Failure) is returned
example1 =  [Step "step1" func1, Step "step2" func2, Step "step3" func3, Step "step4" func4]

-- This will fail at step3 (step4 will never be reached)
example2 =  [Step "step1" func1, Step "step2" func2, Step "step3" funcFail, Step "step4" func4]

result :: Either String Result -> String
result (Left s) = s
result (Right (Result r)) = r

main = do
    let f = putStrLn . result . run
    f example1
    f example2 

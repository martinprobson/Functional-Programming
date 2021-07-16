getLine' :: IO String
getLine' = do 
  c <- getChar
  if c == '\n'
     then return ""
     else do
       l <- getLine'
       return (c:l)

getLine'' :: IO String
getLine'' = getChar >>= (\c -> if c == '\n' then return "" else getLine'' >>= (\l -> return (c:l)))

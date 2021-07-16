
reverseWords :: String -> String
reverseWords s = unwords (map reverse (words s))


reverseWords' :: String -> String
reverseWords' s = unwords . map reverse . words $ s

reverseWords'' :: String -> String
reverseWords'' = unwords . map reverse . words 

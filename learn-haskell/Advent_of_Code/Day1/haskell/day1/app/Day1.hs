module Day1 (toTry, handler) where

import System.Environment
import System.IO
import System.IO.Error
import Control.Exception
import Data.List (subsequences)

toTry :: IO () 
toTry = do
    (filename:_) <- getArgs
    contents <- readFile filename
    print . day1 . fmap (read::String->Int) . lines $ contents
    print . day1' . fmap (read::String->Int) . lines $ contents
--    print . length . threes . fmap (read::String->Int) . lines $ contents

handler :: IOError -> IO ()
handler e 
  | isDoesNotExistError e = putStrLn "The file does not exist"
  | otherwise = ioError e

--
--
--
day1 :: (Num a, Eq a) => [a] -> a
day1 = head . fmap product . filter (\x -> sum x == 2020) . threes

day1' :: (Num a, Eq a) => [a] -> a
day1' = head . fmap product . filter (\x -> sum x == 2020) . pair

pair :: (Num a) => [a] -> [[a]]
pair [] = []
pair (x:xs) = pair' x xs ++ pair xs
  where 
    pair' :: Num a => a -> [a] -> [[a]]
    pair' _ [] = []
    pair' n (x:xs) = [n,x] : pair' n xs


threes :: (Num a) => [a] -> [[a]]
threes [] = []
threes (x:xs) = map (x:) (pair xs) ++ threes xs


import System.Environment
import System.IO
import System.IO.Error
import Control.Exception

main = catch toTry handler

toTry :: IO () 
toTry = do
    (filename:_) <- getArgs
    contents <- readFile filename
    putStrLn $ "The file has " ++ show (length (lines contents)) ++ " lines!"

handler :: IOError -> IO ()
handler e 
  | isDoesNotExistError e = putStrLn "The file does not exist"
  | otherwise = ioError e

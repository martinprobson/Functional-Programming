module Main where

import Lib

main :: IO ()
main = do
  print $ canReachIn3 (6,2) (6,1)
  print $ canReachIn3' (6,2) (6,1)
  print $ canReachIn3'' (6,2) (6,1)
  print $ canReachIn3''' (6,2) (6,1)
  print $ canReachIn3 (6,2) (7,3)
  print $ canReachIn3' (6,2) (7,3)
  print $ canReachIn3'' (6,2) (7,3)
  print $ canReachIn3''' (6,2) (7,3)

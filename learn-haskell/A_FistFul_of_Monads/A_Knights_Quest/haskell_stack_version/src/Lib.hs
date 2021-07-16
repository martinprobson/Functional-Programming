module Lib
    ( canReachIn3,
      canReachIn3',
      canReachIn3'',
      canReachIn3'''
    ) where

--
-- LYAHFTGG - A fistful of Monads 
--
-- A Knights Quest - Build a program to determine if a Chess Knight and move from 
-- a start position to an end position in three moves.
--
-- Define our chessboard co-ordinates (column 1-8, row 1-8)
type KnightPos = (Int, Int)

-- Generate all possible moves given a starting position
moveKnight :: KnightPos -> [KnightPos]
moveKnight (c,r) = filter onBoard
              [(c-1,r-2),(c+1,r-2),(c+2,r-1)
              ,(c+2,r+1),(c+1,r+2),(c-1,r+2)
              ]
              where onBoard (c,r) = c `elem` [1..8] && r `elem` [1..8]

-- First Version (using Monad bind operator on List)
canReachIn3 :: KnightPos -> KnightPos -> Bool
canReachIn3 start end = end `elem` (moveKnight start >>= moveKnight >>= moveKnight )

-- Second version (using do notation)
canReachIn3' :: KnightPos -> KnightPos -> Bool
canReachIn3' start end = end `elem` do
  f <- moveKnight start
  s <- moveKnight f
  moveKnight s

-- Third version (using a fold)
canReachIn3'' :: KnightPos -> KnightPos -> Bool
canReachIn3'' start end = end `elem` foldl (>>=) [start] [moveKnight,moveKnight,moveKnight]

--
--  Now we have re-written using a fold, we can generalize
--  the canReachIn3 function to canReachInN
canReachInN :: KnightPos -> KnightPos -> Int -> Bool
canReachInN start end n = end `elem` foldl (>>=) [start] (replicate n moveKnight)

canReachIn3''' :: KnightPos -> KnightPos -> Bool
canReachIn3''' start end = canReachInN start end 3


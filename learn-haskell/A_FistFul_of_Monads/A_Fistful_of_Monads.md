
# A Fistful of Monads

## Exercise - A Knight's Quest
Imagine you have a chess board with only one knight piece on it. We want to find out if the knight can reach a certain position in three moves.
We can use the numbers 1 to 8 to represent the row and column position of the knight. The first number will determine the column and the seond the row.

```haskell
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
  t <- moveKnight s
  return t

-- Third version (using a fold)
canReachIn3'' :: KnightPos -> KnightPos -> Bool
canReachIn3'' start end = end `elem` (foldl (>>=) [start] [moveKnight,moveKnight,moveKnight])

--
--  Now we have re-written using a fold, we can generalize
--  the canReachIn3 function to canReachInN
canReachInN :: KnightPos -> KnightPos -> Int -> Bool
canReachInN start end n = end `elem` (foldl (>>=) [start] (replicate n moveKnight))

canReachIn3''' :: KnightPos -> KnightPos -> Bool
canReachIn3''' start end = canReachInN start end 3

main = do
  putStrLn . show $ canReachIn3 (6,2) (6,1)
  putStrLn . show $ canReachIn3' (6,2) (6,1)
  putStrLn . show $ canReachIn3'' (6,2) (6,1)
  putStrLn . show $ canReachIn3''' (6,2) (6,1)
  putStrLn . show $ canReachIn3 (6,2) (7,3)
  putStrLn . show $ canReachIn3' (6,2) (7,3)
  putStrLn . show $ canReachIn3'' (6,2) (7,3)
  putStrLn . show $ canReachIn3''' (6,2) (7,3)
```

```scala
object AKnightsQuest {

  type KnightPos = (Int,Int)

  def moveKnight(s: KnightPos): List[KnightPos] = {
    def onBoard(s: KnightPos):Boolean = Range(1,8).contains(s._1) && Range(1,8).contains(s._2)
    s  match {
      case (c,r) => List((c-1,r-2),(c+1,r-2),(c+2,r-1),
        (c+2,r+1),(c+1,r+2),(c-1,r+2)).filter(onBoard)
    }
  }

  //
  // First version (using Monad bind operator on a list)
  //
  def canReachIn3(start: KnightPos, end: KnightPos): Boolean = moveKnight(start).flatMap(moveKnight).flatMap(moveKnight).contains(end)

  //
  // Second version (using for comprehension)
  //
  def _canReachIn3(start: KnightPos, end: KnightPos): Boolean = {
    val r = for {
      f <- moveKnight(start)
      s <- moveKnight(f)
      t <- moveKnight(s)
    } yield t
    r.contains(end)
  }

  //
  // Third version (using a fold)
  //
  def __canReachIn3(start: KnightPos, end: KnightPos): Boolean =
    List[KnightPos => List[KnightPos]](moveKnight,moveKnight,moveKnight)
      .foldLeft(List(start))( (z, p) => z.flatMap(p)).contains(end)

  //
  // Now we have re-written using a fold, we can generalize to canReachInN
  //
  def canReachInN(start: KnightPos, end: KnightPos, numMoves: Int): Boolean =
    List.fill[KnightPos => List[KnightPos]](numMoves)(moveKnight)
      .foldLeft(List(start))( (z, p) => z.flatMap(p)).contains(end)

  //
  // Forth version (using a canReachInN)
  //
  def ___canReachIn3(start: KnightPos, end: KnightPos): Boolean = canReachInN(start,end,3)
}

object Main extends App {
  println(AKnightsQuest.canReachIn3((6,2),(6,1)))
  println(AKnightsQuest._canReachIn3((6,2),(6,1)))
  println(AKnightsQuest.__canReachIn3((6,2),(6,1)))
  println(AKnightsQuest.___canReachIn3((6,2),(6,1)))
  println(AKnightsQuest.canReachIn3((6,2),(7,3)))
  println(AKnightsQuest._canReachIn3((6,2),(7,3)))
  println(AKnightsQuest.__canReachIn3((6,2),(7,3)))
  println(AKnightsQuest.___canReachIn3((6,2),(7,3)))
}

```

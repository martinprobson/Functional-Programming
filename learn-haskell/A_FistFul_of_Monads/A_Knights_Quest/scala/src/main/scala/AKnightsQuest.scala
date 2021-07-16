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
}

package minesweeper

abstract class Area(ms: Minesweeper, row: Int, col: Int) {
  private val movePattern = Seq((0, 0), (-1, 0), (1, 0), (0, -1), (0, 1))
  lazy val mineCount: Int = movePattern.foldLeft(0)((sum, move) => {
    val nextRow = row + move._1
    val nextCol = col + move._2
    if (nextRow >= 0 && nextRow < ms.sizeRow && nextCol >= 0 && nextCol < ms.sizeCol)
      ms.field(nextRow)(nextCol) match {
        case _: MineArea => sum + 1
        case _ => sum
      }
    else sum
  })
}

case class NormalArea(ms: Minesweeper, row: Int, col: Int)
  extends Area(ms: Minesweeper, row: Int, col: Int)
case class MineArea(ms: Minesweeper, row: Int, col: Int)
  extends Area(ms: Minesweeper, row: Int, col: Int)

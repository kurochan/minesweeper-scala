package minesweeper

abstract class Area(ms: Minesweeper,
  val row: Int, val col: Int,
  val flag: Boolean = false, val open: Boolean = false) {

  private val movePattern = Seq((0, 0), (-1, 0), (1, 0), (0, -1), (0, 1))

  lazy val mineCount: Int = movePattern.foldLeft(0)((sum, move) => {
    val nextRow = row + move._1
    val nextCol = col + move._2
    if (nextRow >= 0 && nextRow < ms.sizeRow && nextCol >= 0 && nextCol < ms.sizeCol)
      ms(nextRow)(nextCol) match {
        case _: MineArea => sum + 1
        case _ => sum
      }
    else sum
  })
}

case class NormalArea(ms: Minesweeper,
  override val row: Int, override val col: Int,
  override val flag: Boolean = false, override val open: Boolean = false)
    extends Area(ms: Minesweeper, row: Int, col: Int, flag: Boolean, open: Boolean)

case class MineArea(ms: Minesweeper,
  override val row: Int, override val col: Int,
  override val flag: Boolean = false, override val open: Boolean = false)
    extends Area(ms: Minesweeper, row: Int, col: Int, flag: Boolean, open: Boolean)

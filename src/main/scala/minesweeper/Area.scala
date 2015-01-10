package minesweeper

abstract class Area(ms: Minesweeper,
  val row: Int, val col: Int,
  val flag: Boolean = false, val isOpen: Boolean = false) {

  private val movePattern = Seq((-1, -1), (-1, 0), (-1, 1),
                                ( 0, -1),          ( 0, 1),
                                ( 1, -1), ( 1, 0), ( 1, 1))

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

  def open(): Minesweeper
}

class NormalArea(ms: Minesweeper,
  override val row: Int, override val col: Int,
  override val flag: Boolean = false, override val isOpen: Boolean = false)
    extends Area(ms: Minesweeper, row: Int, col: Int, flag: Boolean, isOpen: Boolean) {

  def open(): Minesweeper = {
    ms.update(new NormalArea(null, row, col, flag, true))
  }
}

class MineArea(ms: Minesweeper,
  override val row: Int, override val col: Int,
  override val flag: Boolean = false, override val isOpen: Boolean = false)
    extends NormalArea(ms: Minesweeper, row: Int, col: Int, flag: Boolean, isOpen: Boolean) {

  override def open() : Minesweeper = {
    ms.update(new MineArea(null, row, col, flag, true))
  }
}

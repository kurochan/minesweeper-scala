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
  def check(): Minesweeper
}

class NormalArea(ms: Minesweeper,
  override val row: Int, override val col: Int,
  override val flag: Boolean = false, override val isOpen: Boolean = false)
    extends Area(ms: Minesweeper, row: Int, col: Int, flag: Boolean, isOpen: Boolean) {

  def open(): Minesweeper = {
    if (flag) ms else ms.update(update(row, col, flag, true))
  }

  def check(): Minesweeper = {
    ms.update(update(row, col, !flag, isOpen))
  }

  def update(row: Int, col: Int, flag: Boolean, isOpen: Boolean): Area =
    new NormalArea(null, row, col, flag, isOpen)
}

class MineArea(ms: Minesweeper,
  override val row: Int, override val col: Int,
  override val flag: Boolean = false, override val isOpen: Boolean = false)
    extends NormalArea(ms: Minesweeper, row: Int, col: Int, flag: Boolean, isOpen: Boolean) {

  override def update(row: Int, col: Int, flag: Boolean, isOpen: Boolean): Area =
    new MineArea(null, row, col, flag, isOpen)
}

package minesweeper

case class Minesweeper(val sizeRow: Int, val sizeCol: Int) {
  private val mines = Set(1, 5, 6)
  private val field: Seq[Seq[Area]] = (0 to sizeRow - 1).map(
    row => (0 to sizeCol - 1).map(col =>
      if (mines(row * sizeCol + col)) MineArea(this, row, col) else NormalArea(this, row, col)))

  def apply(row: Int)(col: Int): Area = field(row)(col)
}

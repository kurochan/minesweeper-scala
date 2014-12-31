package minesweeper

import scala.util.Random
import scala.annotation.tailrec

sealed abstract class Minesweeper() {
  val sizeRow: Int
  val sizeCol: Int
  def apply(row: Int)(col: Int): Area
}

object Minesweeper {

  def apply(sizeRow: Int, sizeCol: Int, mineNum: Int) = {
    new Playing(sizeRow, sizeCol, mineNum)
  }
}

class Playing(val sizeRow: Int, val sizeCol: Int, mineNum: Int) extends
  Minesweeper {

  private val mines: Set[Int] =
    Random.shuffle(0 to sizeRow * sizeCol - 1).slice(0, mineNum).toSet

  private val field: Seq[Seq[Area]] = (0 to sizeRow - 1).map(
    row => (0 to sizeCol - 1).map(col =>
      if (mines(row * sizeCol + col)) MineArea(this, row, col) else NormalArea(this, row, col)))

  def apply(row: Int)(col: Int): Area = field(row)(col)
}

class Cleared(ms: Minesweeper) extends Minesweeper {
  val sizeRow = ms.sizeRow
  val sizeCol = ms.sizeRow

  def apply(row: Int)(col: Int): Area = ms(row)(col)
}

class Dead(ms: Playing) extends Cleared(ms: Playing)

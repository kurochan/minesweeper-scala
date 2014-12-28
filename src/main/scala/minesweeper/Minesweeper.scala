package minesweeper

import scala.util.Random
import scala.annotation.tailrec

case class Minesweeper(sizeRow: Int, sizeCol: Int, mineNum: Int) {
  private val mines: Set[Int] = {
    @tailrec
    def mkMines(set: Set[Int] = Set()): Set[Int] =
      if (set.size != mineNum) mkMines(set + Random.nextInt % sizeRow * sizeCol) else set
    mkMines()
  }
  private val field: Seq[Seq[Area]] = (0 to sizeRow - 1).map(
    row => (0 to sizeCol - 1).map(col =>
      if (mines(row * sizeCol + col)) MineArea(this, row, col) else NormalArea(this, row, col)))

  def apply(row: Int)(col: Int): Area = field(row)(col)
}

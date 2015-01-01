package minesweeper

import scala.util.Random
import scala.annotation.tailrec

sealed abstract class Minesweeper() {
  val sizeRow: Int
  val sizeCol: Int
  val countOpen: Int
  def apply(row: Int)(col: Int): Area
}

object Minesweeper {

  def initMines(size: Int, mineNum: Int): Set[Int] =
    Random.shuffle(0 to size - 1).slice(0, mineNum).toSet

  def apply(sizeRow: Int, sizeCol: Int, mineNum: Int) = {
    new Playing(sizeRow, sizeCol, 0, initMines(sizeRow * sizeCol, mineNum))
  }
}

class Playing(val sizeRow: Int, val sizeCol: Int, val countOpen: Int,
  mines: Set[Int], prevField: Option[Seq[Seq[Area]]] = None) extends Minesweeper {

  private val field: Seq[Seq[Area]] = prevField match {
    case Some(field) =>
      field.map(_.map(area => NormalArea(this, area.row, area.col)))

    case None => (0 to sizeRow - 1).map(
      row => (0 to sizeCol - 1).map(col => if (mines(row * sizeCol + col))
        new MineArea(this, row, col) else new NormalArea(this, row, col)))
  }

  def apply(row: Int)(col: Int): Area = field(row)(col)
}

class Cleared(override val sizeRow: Int, override val sizeCol: Int, countOpen: Int,
  mines: Set[Int], prevField: Option[Seq[Seq[Area]]] = None) extends
    Playing(sizeRow: Int, sizeCol: Int, countOpen: Int, mines: Set[Int], prevField: Option[Seq[Seq[Area]]])

class Dead(override val sizeRow: Int, override val sizeCol: Int, countOpen: Int,
  mines: Set[Int], prevField: Option[Seq[Seq[Area]]] = None) extends
    Playing(sizeRow: Int, sizeCol: Int, countOpen: Int, mines: Set[Int], prevField: Option[Seq[Seq[Area]]])

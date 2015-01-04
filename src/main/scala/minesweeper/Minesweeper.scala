package minesweeper

import scala.util.Random
import scala.annotation.tailrec

sealed abstract class Minesweeper() {
  val sizeRow: Int
  val sizeCol: Int
  val countOpen: Int
  def apply(row: Int)(col: Int): Area
  def update(area: Area): Minesweeper
}

object Minesweeper {

  def initMines(size: Int, mineNum: Int): Set[Int] =
    Random.shuffle(0 to size - 1).slice(0, mineNum).toSet

  def apply(sizeRow: Int, sizeCol: Int, mineNum: Int): Minesweeper = {
    new Playing(sizeRow, sizeCol, 0, initMines(sizeRow * sizeCol, mineNum))
  }
}

class Playing(val sizeRow: Int, val sizeCol: Int, val countOpen: Int,
  mines: Set[Int], prevField: Seq[Seq[Area]] = null) extends Minesweeper {

  private val field: Seq[Seq[Area]] = Option(prevField) match {
    case Some(field) =>
      field.map(_.map(_ match {
        case area: MineArea => new MineArea(this, area.row, area.col, area.flag, area.isOpen)
        case area => new NormalArea(this, area.row, area.col, area.flag, area.isOpen)
      }))

    case None => (0 to sizeRow - 1).map(
      row => (0 to sizeCol - 1).map(col => if (mines(row * sizeCol + col))
        new MineArea(this, row, col) else new NormalArea(this, row, col)))
  }

  def apply(row: Int)(col: Int): Area = field(row)(col)

  def update(area: Area): Minesweeper = {
    val newField = field.updated(area.row, field(area.row).updated(area.col, area))
    new Playing(sizeRow, sizeCol, countOpen, mines, newField)
  }
}

class Cleared(override val sizeRow: Int, override val sizeCol: Int, countOpen: Int,
  mines: Set[Int], prevField: Seq[Seq[Area]] = null) extends
    Playing(sizeRow: Int, sizeCol: Int, countOpen: Int, mines: Set[Int], prevField: Seq[Seq[Area]])

class Dead(override val sizeRow: Int, override val sizeCol: Int, countOpen: Int,
  mines: Set[Int], prevField: Seq[Seq[Area]] = null) extends
    Playing(sizeRow: Int, sizeCol: Int, countOpen: Int, mines: Set[Int], prevField: Seq[Seq[Area]])

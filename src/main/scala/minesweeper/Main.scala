package minesweeper

import minesweeper.ui.cui.CUI
import scala.util.control.Exception._

object Main {

  def main(args: Array[String]) {
    val row = if (args.size > 0) safeStringToInt(args(0)).getOrElse(5) else 5
    val col = if (args.size > 1) safeStringToInt(args(1)).getOrElse(5) else 5
    val mines = if (args.size > 2) safeStringToInt(args(2)).getOrElse(5) else 5
    val ms = Minesweeper(row, col, mines)
    CUI(ms).attach
  }

  def safeStringToInt(str: String): Option[Int] = {
    catching(classOf[NumberFormatException]) opt str.toInt
  }
}

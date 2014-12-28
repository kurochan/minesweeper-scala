package minesweeper

import minesweeper.ui.cui.CUI

object Main {
  def main(args: Array[String]): Unit = {
    val ms = new Minesweeper(5, 5)
    new CUI(ms).attach
  }
}

package minesweeper

import minesweeper.ui.cui.CUI

object Main {
  def main(args: Array[String]) {
    val ms = Minesweeper(5, 5, 5)
    CUI(ms).attach
  }
}

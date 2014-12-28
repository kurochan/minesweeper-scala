package minesweeper.ui.cui

import scala.annotation.tailrec
import minesweeper.Minesweeper

class CUI(ms: Minesweeper) {
  val colNames:Seq[String] = {
    @tailrec
    def mkCode(seq: Seq[Int], quot: Int): Seq[Int] = {
      val newQuot = quot / 27
      val newSeq = quot % 27 +: seq
      if (newQuot > 0) mkCode(newSeq, newQuot) else newSeq
    }
    val size = (0 to mkCode(Seq(), ms.sizeCol).size - 2)
      .foldLeft(0)((m, n) => m + Math.pow(10, n).toInt) + ms.sizeCol
    (1 to size).map(n =>
      mkCode(Seq(), n).foldLeft("")((m, n) => {
        m + (n + 'a' - 1).toChar
      })).filter(! _.contains("`"))
  }

  val rowNameWidth = ms.sizeRow.toString.size + 1
  lazy val colNameWidth = colNames.last.size + 1

  def attach() {
    printField
  }

  def printField() {
    print(" " * rowNameWidth)
    colNames.foreach(name => print(name + " " * (colNameWidth - name.size)))
    println
    for(row <- 0 to ms.sizeRow - 1) {
      print(row + " " * (rowNameWidth - row.toString.size))
      for(col <- 0 to ms.sizeCol - 1) {
        print(ms(row)(col).mineCount + " " * (colNameWidth - 1))
      }
      println
    }
  }
}


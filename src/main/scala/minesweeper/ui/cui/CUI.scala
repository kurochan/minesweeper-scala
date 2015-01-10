package minesweeper.ui.cui

import java.util.Scanner
import scala.annotation.tailrec
import minesweeper.{Minesweeper, Cleared, Dead, Area, MineArea}

case class CUI(ms: Minesweeper) {

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
    val sc = new java.util.Scanner(System.in)
    @tailrec
    def loop(ms: Minesweeper) {
      println
      printField(ms)
      println

      ms match {
        case _: Cleared => {
          println("Cleared!")
          println
          printField(ms, true)
        }
        case _: Dead => {
          println("Dead!")
          println
          printField(ms, true)
        }
        case _ => {
          println("Select field (row col)")
          val row, col = sc.nextInt
          loop(ms(row)(col).open)
        }
      }
    }
    loop(ms)
  }

  def printField(ms: Minesweeper, debug: Boolean = false) {
    print(" " * rowNameWidth)
    colNames.foreach(name => print(name + " " * (colNameWidth - name.size)))
    println

    for(row <- 0 to ms.sizeRow - 1) {
      print(row + " " * (rowNameWidth - row.toString.size))
      for(col <- 0 to ms.sizeCol - 1) {
        val area = ms(row)(col)
        val mark = (area.isOpen || debug) match {
          case true => area match {
            case _: MineArea => "x"
            case _ => area.mineCount.toString
          }
          case false => area.flag match {
            case true => "*"
            case false => "?"
          }
        }
        print(mark + " " * (colNameWidth - 1))
      }
      println
    }
  }
}


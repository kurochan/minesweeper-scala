package minesweeper

import org.specs2.mutable.SpecificationWithJUnit

class MinesweeperSpec extends SpecificationWithJUnit {
  "Minesweeper" should {
    val ms = Minesweeper(10, 15, 5)

    "be initialized" in {
      ms.sizeRow must_== 10
      ms.sizeCol must_== 15
      ms.countOpen must_== 0
    }
  }
}

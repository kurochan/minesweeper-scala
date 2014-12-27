package minesweeper

import org.specs2.mutable.SpecificationWithJUnit

class MainSpec extends SpecificationWithJUnit {
  "Empty Test" should {
    "be return true" in {
        Main.main(Array())
        true
    }
  }
}

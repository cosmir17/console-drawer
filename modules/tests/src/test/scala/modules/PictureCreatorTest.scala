package modules

import modules.CommandParser._
import modules.PictureCreator.{Canvas, execute}
import modules.PicturePrinter._
import org.specs2.mutable.Specification

class PictureCreatorTest extends Specification {
  val twentyByFourCanvas = """----------------------
                             ||                    |
                             ||                    |
                             ||                    |
                             ||                    |
                             |----------------------""".stripMargin
  val lineDrawnOne       = """----------------------
                             ||                    |
                             ||xxxxxx              |
                             ||                    |
                             ||                    |
                             |----------------------""".stripMargin
  val lineDrawnTwo       = """----------------------
                             ||                    |
                             ||xxxxxx              |
                             ||     x              |
                             ||     x              |
                             |----------------------""".stripMargin
  val rectangle          = """----------------------
                             ||             xxxxx  |
                             ||xxxxxx       x   x  |
                             ||     x       xxxxx  |
                             ||     x              |
                             |----------------------""".stripMargin

  "Parser" should {
    "take c cmd" in {
      "with zero input and create a canvas" in {
        execute(CanvasCmd(0, 0), None).convertToStr() mustEqual ""
      }
      "with 20 4 and create a canvas" in {
        execute(CanvasCmd(20, 4), None).convertToStr() mustEqual twentyByFourCanvas
      }
      "with 2 2 and create a canvas" in {
        execute(CanvasCmd(2, 2), None).convertToStr() mustEqual """----
                                                              ||  |
                                                              ||  |
                                                              |----""".stripMargin
      }
      "with 1 1 and create a canvas" in {
        execute(CanvasCmd(1, 1), None).convertToStr() mustEqual """---
                                                              || |
                                                              |---""".stripMargin
      }
    }

    "take l cmd" in {
      "with 1 2 6 2 and draw a line" in {
        val canvasReversed = twentyByFourCanvas.convertToCanvas()
        canvasReversed.convertToStr() mustEqual twentyByFourCanvas
        execute(LineCmd(1, 2, 6, 2), Some(canvasReversed)).convertToStr() mustEqual lineDrawnOne
      }
      "with 3 2 3 2 and draw a line" in {
        val canvasReversed = twentyByFourCanvas.convertToCanvas()
        execute(LineCmd(3, 2, 3, 2), Some(canvasReversed)).convertToStr() mustEqual
          """----------------------
            ||                    |
            ||  x                 |
            ||                    |
            ||                    |
            |----------------------""".stripMargin
      }
      "with 3 2 3 2 and draw a line" in {
        val canvasReversed = twentyByFourCanvas.convertToCanvas()
        execute(LineCmd(3, 2, 5, 4), Some(canvasReversed)).convertToStr() mustEqual
          """----------------------
            ||                    |
            ||  x                 |
            ||   x                |
            ||    x               |
            |----------------------""".stripMargin
      }
      "with canvas 1 2 6 2 and draw a line 6 3 6 4" in {
        val canvasReversed = lineDrawnOne.convertToCanvas()
        execute(LineCmd(6, 3, 6, 4), Some(canvasReversed)).convertToStr() mustEqual lineDrawnTwo
      }
      "with 6 3 6 4 without a canvas" in {
        execute(LineCmd(6, 3, 6, 4), None) must throwA(new IllegalArgumentException("Please draw a canvas first"))
      }
      "and draw a line much bigger than the canvas" in {
        val canvasReversed = lineDrawnOne.convertToCanvas()
        execute(LineCmd(20, 50, 30, 60), Some(canvasReversed)).convertToStr() must throwA(new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command"))
      }
    }

    "take r cmd" in {
      "with 14 1 18 3 and draw a rectangle" in {
        val canvasReversed = lineDrawnTwo.convertToCanvas()
        execute(RectangleCmd(14, 1, 18, 3), Some(canvasReversed)).convertToStr() mustEqual rectangle
      }
      "and draw a rectangle much bigger than the canvas" in {
        val canvasReversed = lineDrawnTwo.convertToCanvas()
        execute(RectangleCmd(20, 50, 30, 60), Some(canvasReversed)).convertToStr() must throwA(new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command"))
      }
      "with 14 1 18 3 without a canvas" in {
        execute(RectangleCmd(14, 1, 18, 3), None) must throwA(new IllegalArgumentException("Please draw a canvas first"))
      }
    }

    "take b cmd" in {
      "with 10 3 o and fill in the contacted space" in {
        val canvasReversed = rectangle.convertToCanvas()
        execute(BucketFillCmd(10, 3, "o"), Some(canvasReversed)).convertToStr() mustEqual
          """----------------------
            ||oooooooooooooxxxxxoo|
            ||xxxxxxooooooox   xoo|
            ||     xoooooooxxxxxoo|
            ||     xoooooooooooooo|
            |----------------------""".stripMargin
      }
      "with 10 3 o and without a canvas" in {
        execute(BucketFillCmd(10, 3, "o"), None) must throwA(new IllegalArgumentException("Please draw a canvas first"))
      }
      "and select a coordinate much bigger than the canvas" in {
        val canvasReversed = lineDrawnTwo.convertToCanvas()
        execute(BucketFillCmd(20, 50, "o"), Some(canvasReversed)).convertToStr() must throwA(new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command"))
      }
    }
  }

  implicit class ForTest(canvas: String) {
    def convertToCanvas(): Canvas = {
      val rows = canvas.split("\n")
      val c = Array.ofDim[String](rows.size, rows(0).length)
      for(i <-0 until rows.size; j <- 0 until rows(0).length) {
        c(i)(j) = rows(i).substring(j, j + 1)
      }
      c
    }
  }

}

package modules

import modules.CommandParser._
import modules.PictureCreator.execute
import modules.PicturePrinter._
import org.specs2.mutable.Specification

import scala.collection.mutable

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
        canvasReversed.convertToString() mustEqual twentyByFourCanvas
        execute(LineCmd(1, 2, 6, 2), Some(mutable.Stack(canvasReversed))).convertToStr() mustEqual lineDrawnOne
      }
      "with 3 2 3 2 and draw a line" in {
        val canvasReversed = twentyByFourCanvas.convertToCanvas()
        execute(LineCmd(3, 2, 3, 2), Some(mutable.Stack(canvasReversed))).convertToStr() mustEqual
          """----------------------
            ||                    |
            ||  x                 |
            ||                    |
            ||                    |
            |----------------------""".stripMargin
      }
      "with 3 2 3 2 and draw a line" in {
        val canvasReversed = twentyByFourCanvas.convertToCanvas()
        execute(LineCmd(3, 2, 5, 4), Some(mutable.Stack(canvasReversed))).convertToStr() mustEqual
          """----------------------
            ||                    |
            ||  x                 |
            ||   x                |
            ||    x               |
            |----------------------""".stripMargin
      }
      "with canvas 1 2 6 2 and draw a line 6 3 6 4" in {
        val canvasReversed = lineDrawnOne.convertToCanvas()
        execute(LineCmd(6, 3, 6, 4), Some(mutable.Stack(canvasReversed))).convertToStr() mustEqual lineDrawnTwo
      }
      "with 6 3 6 4 without a canvas" in {
        execute(LineCmd(6, 3, 6, 4), None) must throwA(new IllegalArgumentException("Please draw a canvas first"))
      }
      "and draw a line much bigger than the canvas" in {
        val canvasReversed = lineDrawnOne.convertToCanvas()
        execute(LineCmd(20, 50, 30, 60), Some(mutable.Stack(canvasReversed))).convertToStr() must throwA(new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command"))
      }
    }

    "take r cmd" in {
      "with 14 1 18 3 and draw a rectangle" in {
        val canvasReversed = lineDrawnTwo.convertToCanvas()
        execute(RectangleCmd(14, 1, 18, 3), Some(mutable.Stack(canvasReversed))).convertToStr() mustEqual rectangle
      }
      "and draw a rectangle much bigger than the canvas" in {
        val canvasReversed = lineDrawnTwo.convertToCanvas()
        execute(RectangleCmd(20, 50, 30, 60), Some(mutable.Stack(canvasReversed))).convertToStr() must throwA(new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command"))
      }
      "with 14 1 18 3 without a canvas" in {
        execute(RectangleCmd(14, 1, 18, 3), None) must throwA(new IllegalArgumentException("Please draw a canvas first"))
      }
    }

    "take b cmd" in {
      "with 10 3 o and fill in the contacted space" in {
        val canvasReversed = rectangle.convertToCanvas()
        execute(BucketFillCmd(10, 3, "o"), Some(mutable.Stack(canvasReversed))).convertToStr() mustEqual
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
        execute(BucketFillCmd(20, 50, "o"), Some(mutable.Stack(canvasReversed))).convertToStr() must throwA(new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command"))
      }
    }

    "take u cmd" in {
      "undo a line to an empty canvas" in {
        val emptyCanvas = twentyByFourCanvas.convertToCanvas()
        val stack = mutable.Stack(emptyCanvas)
        val lineDrawn = lineDrawnOne.convertToCanvas()
        stack.push(lineDrawn)
        stack.size mustEqual 2
        val result = execute(UndoCmd, Some(stack))
        result.size mustEqual 1
        result.convertToStr() mustEqual twentyByFourCanvas
        result.size mustEqual 1
      }

      "undo a rectangle to an empty canvas" in {
        val emptyCanvas = twentyByFourCanvas.convertToCanvas()
        val stack = mutable.Stack(emptyCanvas)
        val lineDrawn = lineDrawnOne.convertToCanvas()
        stack.push(lineDrawn)
        val lineDrawn2 = lineDrawnTwo.convertToCanvas()
        stack.push(lineDrawn2)
        val rectangleDrawn = rectangle.convertToCanvas()
        stack.push(rectangleDrawn)
        stack.size mustEqual 4
        val result = execute(UndoCmd, Some(stack))
        result.size mustEqual 3
        result.convertToStr() mustEqual lineDrawnTwo
        result.size mustEqual 3
      }

      "for an empty canvas" in {
        val emptyCanvas = twentyByFourCanvas.convertToCanvas()
        val stack = mutable.Stack(emptyCanvas)
        stack.size mustEqual 1
        val result = execute(UndoCmd, Some(stack))
        result.size mustEqual 0
        result.convertToStr() mustEqual ""
        result.size mustEqual 0
      }

      "throw an exception if there is no canvas" in {
        val stack = mutable.Stack[Canvas]()
        stack.size mustEqual 0
        execute(UndoCmd, Some(stack)).convertToStr() must throwA(new IllegalArgumentException("Please draw a canvas first"))
      }
    }
  }

  implicit class ForTest(canvas: String) {
    def convertToCanvas(): Canvas = {
      val rows = canvas.split("\n")
      val c = Canvas(rows(0).length - 2, rows.length - 2)
      for (i <- 0 until rows(0).length; j <- 0 until rows.length) {
        c.assign(i, j, rows(j).substring(i, i + 1))
      }
      c
    }
  }

}

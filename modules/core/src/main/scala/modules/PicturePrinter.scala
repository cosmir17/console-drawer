package modules

import modules.PictureCreator.Canvas

import scala.collection.mutable

object PicturePrinter {
  def convertToString(image: Canvas): String = image.map(_.mkString).mkString("\n")

  implicit class Printer(canvas: Canvas) {
    def convertToStr(): String = PicturePrinter.convertToString(canvas)
  }

  implicit class StackPrinter(canvas: mutable.Stack[Canvas]) {
    def convertToStr(): String =
      if (canvas.isEmpty) ""
      else PicturePrinter.convertToString(canvas.pop())
  }
}

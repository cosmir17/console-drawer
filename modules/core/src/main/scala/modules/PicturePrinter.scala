package modules

import modules.PictureCreator.Canvas

object PicturePrinter {
  def convertToString(image: Canvas): String = image.map(_.mkString).mkString("\n")

  implicit class Printer(canvas: Canvas) {
    def convertToStr(): String = PicturePrinter.convertToString(canvas)
  }
}

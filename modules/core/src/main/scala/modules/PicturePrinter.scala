package modules

import scala.collection.mutable

object PicturePrinter {

  implicit class StackPrinter(stack: mutable.Stack[Canvas]) {
    def convertToStr(): String =
      if (stack.isEmpty) ""
      else {
        val canvas = stack.pop()
        stack.push(canvas)
        canvas.convertToString()
      }
  }
}

package modules

import modules.CommandParser._

object PictureCreator {
  type Canvas = Array[Array[String]]

  def execute(cmd: CommandParser.Cmd, canvas: Option[Canvas]): Canvas =
    (cmd, canvas) match {
      case (CanvasCmd(0, 0), _) =>
        Array.ofDim[String](0, 0)
      case (CanvasCmd(width, height), _) =>
        val arr = Array.ofDim[String](height + 2, width + 2)
        for(i<-0 until height + 2; j<-0 until width + 2) { arr(i)(j) = " " }
        for(i<-0 until height + 2) { arr(i)(0) = "|"; arr(i)(width + 1) = "|" }
        for(j<-0 until width + 2) { arr(0)(j) = "-"; arr(height + 1)(j) = "-" }
        arr
      case (LineCmd(x1, y1, x2, y2), Some(c)) if (x1 == x2 || y1 == y2) && checkIfWithinCanvas(x1, y1, x2, y2, c) => //this is for a horizontal or a vertical line
        for(i <- y1 to y2; j <- x1 to x2) { c(i)(j) = "x" }
        c
      case (LineCmd(x1, y1, x2, y2), Some(c)) if checkIfWithinCanvas(x1, y1, x2, y2, c) => //this is for a diagonal line
        for { (i, j) <- (y1 to y2) zip (x1 to x2) } { c(i)(j) = "x" } //zip is to iterate over two arrays (diagonal line: width and height are same)
        c
      case (RectangleCmd(x1, y1, x2, y2), Some(c)) if checkIfWithinCanvas(x1, y1, x2, y2, c)  =>
        for(i <- y1 to y2; j <- x1 to x2) { if(i == y1 || i == y2 || j == x1 || j == x2) {c(i)(j) = "x"} }
        c
      case (BucketFillCmd(x, y, colour), Some(c)) if checkIfWithinCanvas(x, y, c) =>
        val xyDefaultColour = c(y)(x); detectSameTilePaint(c, y, x, xyDefaultColour, colour)
        c
      case (_, Some(_)) => throw new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command")
      case (_, None) => throw new IllegalArgumentException("Please draw a canvas first")
    }

  private def detectSameTilePaint(c: Canvas, y: Int, x: Int, fromColour: String, toColour: String): Unit = {
    c(y)(x) = toColour
    val yu = c(y + 1)(x); if (yu == fromColour) detectSameTilePaint(c, y+1, x, fromColour, toColour)
    val yd = c(y - 1)(x); if (yd == fromColour) detectSameTilePaint(c, y-1, x, fromColour, toColour)
    val xu = c(y)(x + 1); if (xu == fromColour) detectSameTilePaint(c, y, x+1, fromColour, toColour)
    val xd = c(y)(x - 1); if (xd == fromColour) detectSameTilePaint(c, y, x-1, fromColour, toColour)
  }

  private def checkIfWithinCanvas(x1: Int, y1: Int, x2: Int, y2: Int, c: Canvas): Boolean =
    c.length > y1 && c.length > y2 && c(0).length > x1 && c(0).length > x2

  private def checkIfWithinCanvas(x: Int, y: Int, c: Canvas): Boolean =
    c.length > y && c(0).length > x
}

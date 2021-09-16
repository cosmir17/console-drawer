package modules

import modules.CommandParser._

import scala.collection.mutable

object PictureCreator {
  def execute(cmd: CommandParser.Cmd, stack: Option[mutable.Stack[Canvas]]): mutable.Stack[Canvas] =
    (cmd, stack) match {
      case (CanvasCmd(0, 0), _) =>
        mutable.Stack[Canvas](Canvas(0, 0))
      case (CanvasCmd(width, height), _) =>
        mutable.Stack[Canvas](Canvas(width, height))
      case (LineCmd(x1, y1, x2, y2), Some(s@mutable.Stack(canvas, _*))) if (x1 == x2 || y1 == y2) && checkIfWithinCanvas(x1, y1, x2, y2, canvas)  => //this is for a horizontal or a vertical line
        val newC = canvas.copy()
        for(i <- x1 to x2; j <- y1 to y2) { newC.assignX(i, j) }
        s.push(newC)
      case (LineCmd(x1, y1, x2, y2), Some(s@mutable.Stack(canvas, _*))) if checkIfWithinCanvas(x1, y1, x2, y2, canvas) => //this is for a diagonal line
        val newC = canvas.copy()
        ((x1 to x2) zip (y1 to y2)) foreach { case (i, j) => newC.assignX(i, j) } //zip is to iterate over two arrays (diagonal line: width and height are same)
        s.push(newC)
      case (RectangleCmd(x1, y1, x2, y2), Some(s@mutable.Stack(canvas, _*))) if checkIfWithinCanvas(x1, y1, x2, y2, canvas) =>
        val newC = canvas.copy()
        for(i <- x1 to x2; j <- y1 to y2) { if(i == x1 || i == x2 || j == y1 || j == y2) { newC.assignX(i, j)} }
        s.push(newC)
      case (BucketFillCmd(x, y, colour), Some(s@mutable.Stack(canvas, _*))) if checkIfWithinCanvas(x, y, canvas)  =>
        val xyDefaultColour = canvas.read(x, y)
        val newC = canvas.copy()
        detectSameTilePaint(newC, x, y, xyDefaultColour, colour)
        s.push(newC)
      case (UndoCmd, Some(mutable.Stack())) =>
        throw new IllegalArgumentException("Please draw a canvas first")
      case (UndoCmd, Some(s)) =>
        s.pop()
        s
      case (_, Some(_)) => throw new IllegalArgumentException("Please provide a suitable canvas that meets the requirements for your command")
      case (_, None) => throw new IllegalArgumentException("Please draw a canvas first")
    }

  private def detectSameTilePaint(c: Canvas, x: Int, y: Int, fromColour: String, toColour: String): Unit = {
    c.assign(x, y, toColour)
    val yu = c.read(x, y + 1); if (yu == fromColour) detectSameTilePaint(c, x, y+1, fromColour, toColour)
    val yd = c.read(x, y - 1); if (yd == fromColour) detectSameTilePaint(c, x, y-1, fromColour, toColour)
    val xu = c.read(x + 1, y); if (xu == fromColour) detectSameTilePaint(c, x+1, y, fromColour, toColour)
    val xd = c.read(x - 1, y); if (xd == fromColour) detectSameTilePaint(c, x-1, y, fromColour, toColour)
  }

  private def checkIfWithinCanvas(x1: Int, y1: Int, x2: Int, y2: Int, c: Canvas): Boolean =
    c.yLength > y1 && c.yLength > y2 && c.xLength > x1 && c.xLength > x2

  private def checkIfWithinCanvas(x: Int, y: Int, c: Canvas): Boolean =
    c.yLength > y && c.xLength > x
}

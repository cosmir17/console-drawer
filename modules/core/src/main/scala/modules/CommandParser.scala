package modules

object CommandParser {
  def execute(input: String): Cmd = input.trim.toLowerCase.split(" ").toList match {
    case ""  :: Nil                                   => throw new IllegalArgumentException("no input is provided")
    case "c" :: w :: h :: Nil if areTwoInt(w, h)      => CanvasCmd(w.toInt, h.toInt)
    case "c" :: _                                     => throw CanvasError
    case "l" :: x1 :: y1 :: x2 :: y2 :: Nil if areFourInt(x1, y1, x2, y2) =>
                                                         LineCmd(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
    case "l" :: _                                     => throw LineError
    case "r" :: x1 :: y1 :: x2 :: y2 :: Nil if areFourInt(x1, y1, x2, y2) =>
                                                         RectangleCmd(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
    case "r" :: _                                     => throw RectangleError
    case "b" :: x :: y :: c :: Nil if areTwoInt(x, y) => BucketFillCmd(x.toInt, y.toInt, c)
    case "b" :: _                                     => throw BucketError
    case "u" :: _                                     => UndoCmd
    case "undo" :: _                                  => UndoCmd
    case "q" :: Nil                                   => Quit
    case "q" :: _                                     => throw QuitError
    case "h" :: _                                     => Help
    case "help" :: _                                  => Help
    case _                                            => throw new IllegalArgumentException(
                                                          "not a supported command, please provide the right input")
  }

  sealed abstract class Cmd(shortCommand: String, longCommand: String)

  val CanvasError = new IllegalArgumentException("c cmd has width and height that need to be numeric")
  case class CanvasCmd(width: Int, height: Int)               extends Cmd("c", "canvas")

  val LineError = new IllegalArgumentException("l cmd has 4 args that need to be numeric")
  case class LineCmd(x1: Int, y1: Int, x2: Int, y2: Int)      extends Cmd("l", "line")

  val RectangleError = new IllegalArgumentException("r cmd has 4 args that need to be numeric")
  case class RectangleCmd(x1: Int, y1: Int, x2: Int, y2: Int) extends Cmd("r", "rectangle")

  val BucketError = new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric")
  case class BucketFillCmd(x: Int, y: Int, colour: String)    extends Cmd("b", "bucket-filled")

  case object UndoCmd    extends Cmd("u", "undo")

  val QuitError = new IllegalArgumentException("q cmd has no args, please just enter q")
  case object Quit                                            extends Cmd("q", "quick")
  case object Help                                            extends Cmd("h", "help")

  private val areTwoInt: (String, String) => Boolean = (x, y) => x.toIntOption.exists(_ >= 0) && y.toIntOption.exists(_ >= 0)
  private val areFourInt: (String, String, String, String) => Boolean =
    (x1, y1, x2, y2) => x1.toIntOption.exists(_ >= 0) && y1.toIntOption.exists(_ >= 0) && x2.toIntOption.exists(_ >= 0) && y2.toIntOption.exists(_ >= 0)

}



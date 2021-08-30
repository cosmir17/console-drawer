import modules.PictureCreator.Canvas
import modules.{CommandParser, PictureCreator}
import modules.PicturePrinter._

import scala.io.StdIn._
import cats.implicits._
import modules.CommandParser.{Help, Quit}
import scala.util.{Try,Success,Failure}

object ConsoleDrawerApp extends App {
  val help = """
               |the program should work as follows:
               | 1. Create a new canvas
               | 2. Start drawing on the canvas by issuing various commands
               | 3. Quit
               |
               |Command 		Description
               |C w h           Should create a new canvas of width w and height h.
               |L x1 y1 x2 y2   Should create a new line from (x1,y1) to (x2,y2). Currently only
               |                horizontal or vertical lines are supported. Horizontal and vertical lines
               |                will be drawn using the 'x' character.
               |R x1 y1 x2 y2   Should create a new rectangle, whose upper left corner is (x1,y1) and
               |                lower right corner is (x2,y2). Horizontal and vertical lines will be drawn
               |                using the 'x' character.
               |B x y c         Should fill the entire area connected to (x,y) with "colour" c. The
               |                behaviour of this is the same as that of the "bucket fill" tool in paint
               |                programs.
               |Q               Should quit the program.
              """.stripMargin

  println("********** Sean's Painting Command Line Tool ***************")
  println("Please enter C Command to create a canvas first, e.g. C 20 4")

  drawingOnCanvas()

  /**
   * common(None).get is not ideal but it's a compromise for foldM method.
   */
  def drawingOnCanvas(): Unit = Try(LazyList.from(0).foldM(common(None).get)((prevImg, _) => repeated(prevImg))) match {
    case Success(_) =>
    case Failure(m) if m.getMessage == "None.get" =>
    case Failure(m) =>
      println(m.getMessage)
      drawingOnCanvas()
  }

  private def repeated(prevImg: Canvas): Option[Canvas] = common(Option(prevImg))

  /**
   * 'common' method can not be used as foldM's second argument. for that reason, 'repeated' method was created.
   * @param canvas
   * @return
   */
  private def common(canvas: Option[Canvas]): Option[Canvas] = {
    println("enter command: e.g. help")
    val command = CommandParser.execute(readLine())
    (command, canvas) match {
      case (Quit, _) =>
        None
      case (Help, pi@ Some(_)) =>
        println(help)
        pi
      case (Help, None) =>
        println(help)
        common(None)
      case (_, prevImg) =>
        val newImage = PictureCreator.execute(command, prevImg)
        println(newImage.convertToStr())
        Option(newImage)
    }
  }

}

/*
The following is how this class can be written using Cats effect. With it, you can write test codes for the console input and the logging.
I didn't use Cats effect because there is a sentence in the Instruction: "Essentially, up to a level where you would be happy to have somebody look at it and judge your result."
Some people have not used Cats effect. I don't want to suggest using new technologies at the firm.
 */

//object FMain {
//  def runConsoleApp[F[_]: Sync : Logger]: F[Unit] = for {
//    canvas <- canvasCmd
//    newPicture <- normalCmd(canvas)
//  } yield ()
//
////    .whileM_(picture => {
////    for {
////      _ <- common(picture)
////    } yield true
////  }) *> Sync[F].delay(ExitCode.Success)
//
//  private def canvasCmd[F[_]: Sync : Logger]: F[Canvas] = for {
//    _        <- Logger[F].info("Please enter C Command to create a canvas first, e.g. C 20 4")
//    _        <- Logger[F].info("enter command:")
//    picture  <- common
//  } yield picture
//
//
//  private def normalCmd[F[_]: Sync : Logger](canvas: Canvas): F[Canvas] = for {
//    _        <- Logger[F].info("enter command: e.g. help")
//    picture  <- common
//  } yield picture
//
//  private def common[F[_]: Sync : Logger]: F[Canvas] = for {
//    input    <- Console.make.readLine
//    cmd      =  CommandParser.execute(input)
//    picture  =  PictureCreator.execute(cmd, None)
//    _        <- PicturePrinter.execute(picture)
//  } yield picture
//}

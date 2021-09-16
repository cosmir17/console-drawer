package modules

object Canvas {
  def apply(width: Int, height: Int) = new Canvas(width, height)
}

class Canvas(array: Array[Array[String]]) {
  def this(width: Int, height: Int) =
    this((width, height) match {
      case (0, 0) =>
        Array.ofDim[String](0, 0)
      case _ =>
        val arr = Array.ofDim[String](height + 2, width + 2)
        for(i<-0 until height + 2; j<-0 until width + 2) { arr(i)(j) = " " }
        for(i<-0 until height + 2) { arr(i)(0) = "|"; arr(i)(width + 1) = "|" }
        for(j<-0 until width + 2) { arr(0)(j) = "-"; arr(height + 1)(j) = "-" }
        arr
    })

  def assignX(x: Int, y: Int): Unit = this.array(y)(x) = "x"
  def read(x: Int, y: Int): String = this.array(y)(x)
  def assign(x: Int, y: Int, c: String): Unit = this.array(y)(x) = c
  def yLength = this.array.length
  def xLength = this.array(0).length

  def convertToString(): String = this.array.map(_.mkString).mkString("\n")
  def copy(): Canvas = new Canvas(this.array.map(_.clone()))
}

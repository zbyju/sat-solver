package stats

case class StatsTracker() {
  private var timeStart: Long = -1
  private var timeEnd: Long = _

  def getTimeStart: Long = timeStart

  def setTimeStart(): Unit = timeStart = System.nanoTime()
  def setTimeStartIfNotSet(): Unit = if(timeStart == -1) timeStart = System.nanoTime()
  def setTimeStart(time: Long): Unit = timeStart = time

  def setTimeEnd(): Unit = timeEnd = System.nanoTime()

  def getTimeNano: Long = timeEnd - timeStart

  override def toString: String = {
    s"""Time: $getTimeNano""".stripMargin
  }
}

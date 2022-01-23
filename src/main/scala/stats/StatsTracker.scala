package stats

case class StatsTracker() {
  private var timeStart: Long = -1
  private var timeEnd: Long = _
  private var progression: Seq[Int] = Seq()
  private val visited = scala.collection.mutable.HashMap.empty[Seq[Boolean], Int]

  def getTimeStart: Long = timeStart

  def setTimeStart(): Unit = timeStart = System.nanoTime()
  def setTimeStartIfNotSet(): Unit = if(timeStart == -1) timeStart = System.nanoTime()
  def setTimeStart(time: Long): Unit = timeStart = time

  def setTimeEnd(): Unit = timeEnd = System.nanoTime()

  def getTimeNano: Long = timeEnd - timeStart

  def addProgress(p: Int): Unit = progression = progression :+ p
  def getProgression: Seq[Int] = progression
  def addVisited(node: Seq[Boolean]) = {
    val count = visited.getOrElse(node, 0)
    visited += (node -> (count + 1))
  }

  def getAverageVisited: Double = visited.values.sum.toDouble / visited.size.toDouble

  def getCountVisited: Int = visited.size

  def getCoverVisited(maxCount: Int): Double = visited.size.toDouble / maxCount.toDouble

  def getMaxVisited: Double = visited.values.max

  override def toString: String = {
    s"""Time: $getTimeNano""".stripMargin
  }
}

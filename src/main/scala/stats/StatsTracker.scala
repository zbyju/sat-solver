package stats

import model.instance.Configuration

case class StatsTracker() {
  private var timeStart: Long = -1
  private var timeEnd: Long = _
  private var progression: Seq[Double] = Seq()
  private var progressionBest: Seq[Double] = Seq()
  private var progressionCost: Seq[Double] = Seq()
//  private val visited = scala.collection.mutable.HashMap.empty[Seq[Boolean], Int]

  def getTimeStart: Long = timeStart

  def setTimeStart(): Unit = timeStart = System.nanoTime()
  def setTimeStartIfNotSet(): Unit = if(timeStart == -1) timeStart = System.nanoTime()
  def setTimeStart(time: Long): Unit = timeStart = time

  def setTimeEnd(): Unit = timeEnd = System.nanoTime()

  def getTimeNano: Long = timeEnd - timeStart

  def addProgress(p: Double): Unit = progression = progression :+ p
  def getProgression: Seq[Double] = progression

  def addProgressBest(best: Option[Configuration]): Unit = best match {
    case Some(b) => progression = progression :+ b.cost
    case None => progression = progression :+ 0
  }
  def getProgressionBest: Seq[Double] = progression

  def addProgressCost(p: Double): Unit = progressionCost = progressionCost :+ p
  def getProgressionCost: Seq[Double] = progressionCost

//  def addVisited(node: Seq[Boolean]) = {
//    val count = visited.getOrElse(node, 0)
//    visited += (node -> (count + 1))
//  }
//
//  def getAverageVisited: Double = visited.values.sum.toDouble / visited.size.toDouble
//
//  def getCountVisited: Int = visited.size
//
//  def getCoverVisited(maxCount: Int): Double = visited.size.toDouble / maxCount.toDouble
//
//  def getMaxVisited: Double = visited.values.max

  override def toString: String = {
    s"""Time: $getTimeNano""".stripMargin
  }
}

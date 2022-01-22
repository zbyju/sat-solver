package model.result

import model.solution.Solution
import stats.StatsTracker

import io.AnsiColor.*

class Result(val id: Int, val setName: String, val sum: Int, isSolution: Boolean,
             val evaluation: Seq[Boolean], val exactSolution: Option[Solution], val statsTracker: StatsTracker) {
  statsTracker.setTimeEnd()


  override def toString = exactSolution match {
    case Some(s) => s"${BLUE}RESULT - $setName (id=$id)${RESET}\nSUM: $sum ($isSolution) vs ${s.sum} (true)\n$evaluation\n${s.evaluation}"
    case None => s""
  }
}

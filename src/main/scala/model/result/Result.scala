package model.result

import model.solution.Solution
import stats.StatsTracker

import io.AnsiColor.*

class Result(val id: Int, val setName: String, val sum: Int, isSolution: Boolean,
             val evaluation: Seq[Boolean], val exactSolution: Option[Solution], val statsTracker: StatsTracker) {
  statsTracker.setTimeEnd()

  def comparisonIcon: String = exactSolution match {
    case Some(s) => if(s.sum == sum) s"${GREEN}✔${RESET}" else if(s.sum > sum) s"${RED}❌${RESET}" else s"${CYAN}?${RESET}"
    case None => s"${CYAN}?${RESET}"
  }

  def toStringCompare: String = exactSolution match {
    case Some(s) => s"${BLUE}RESULT - $setName (id=$id)${RESET} - $sum ($isSolution) vs ${s.sum} (true) " + comparisonIcon
    case None => s"${BLUE}RESULT - $setName (id=$id)${RESET} - $sum ($isSolution) vs No exact solution " + comparisonIcon
  }

  override def toString: String = exactSolution match {
    case Some(s) => s"${BLUE}RESULT - $setName (id=$id)${RESET}\nSUM: $sum ($isSolution) vs ${s.sum} (true)\n$evaluation\n${s.evaluation}"
    case None => s""
  }
}

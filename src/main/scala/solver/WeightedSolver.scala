package solver

import model.instance.WeightedInstance
import model.result.WeightedResult
import stats.StatsTracker

trait WeightedSolver {
  val name: String
  def solve(instance: WeightedInstance, statsTracker: StatsTracker): WeightedResult
}

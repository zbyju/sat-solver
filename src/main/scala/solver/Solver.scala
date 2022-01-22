package solver

import model.instance.Instance
import model.result.Result
import stats.StatsTracker

trait Solver {
  val name: String
  def solve(instance: Instance, statsTracker: StatsTracker): Result
}

package solver.simulatedannealing

import model.instance.Instance
import model.result.Result
import solver.Solver
import stats.StatsTracker

class SimulatedAnnealingWeightedSolver() extends Solver {
  override val name: String = s"SA-"
  override def solve(instance: Instance, statsTracker: StatsTracker): Result = {
    setupSolving(statsTracker)

    null
  }

  def setupSolving(statsTracker: StatsTracker): Unit = {
    statsTracker.setTimeStart()
  }

}

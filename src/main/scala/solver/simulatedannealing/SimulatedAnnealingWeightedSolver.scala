package solver.simulatedannealing

import model.instance.{Instance, WeightedInstance}
import model.result.{Result, WeightedResult}
import solver.WeightedSolver
import stats.StatsTracker
import solver.simulatedannealing.Config

class SimulatedAnnealingWeightedSolver(config: Config) extends WeightedSolver {
  override val name: String = s"SA-${config.initTemperature}-${config.initEquilibrium}-${config.coolingCoefficient}"
  override def solve(instance: WeightedInstance, statsTracker: StatsTracker): WeightedResult = {
    setupSolving(statsTracker)

    null
  }

  def setupSolving(statsTracker: StatsTracker): Unit = {
    statsTracker.setTimeStart()
  }

}

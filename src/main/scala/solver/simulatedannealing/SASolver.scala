package solver.simulatedannealing

import model.instance.{Configuration, Instance}
import model.result.Result
import solver.Solver
import stats.StatsTracker

import scala.math.exp
import scala.util.Random

type Temperature = Double
type Best = Option[Configuration]

class SASolver(config: SAConfig) extends Solver {
  override val name: String = s"SA-"

  def initTempWeightSum(i: Configuration, multiplier: Double = 2): Temperature = {
    i.variables.map(v => v.weight).sum
  }

  def initTempWeightMax(i: Configuration, multiplier: Double = 2): Temperature = {
    i.variables.map(v => v.weight).max
  }

  def frozenStatic(T: Double): Boolean = {
    T <= config.frozenThreshold
  }

  def equilibrium(i: Int): Boolean = {
    i == 0
  }

  def cool(T: Temperature): Temperature = {
    T * config.coolingCoefficient
  }

  def next(i: Configuration, T: Double): Configuration = {
    val nextState = i.nextOneRandom
    if(nextState.isBetter(i)) return nextState
    val delta = i.costDifference(nextState)
    val rnd = new Random()
    if(rnd.nextDouble() < exp(-delta / T)) {
      return nextState
    }
    i
  }

  override def solve(instance: Instance, statsTracker: StatsTracker): Result = {
    var state: SAState = setupSolving(instance, statsTracker)
    while(!frozenStatic(state.temperature)) {
      state = state.changeEqui(config.defaultEqui)
      while(!equilibrium(state.equi)) {
        state = state.changeConfiguration(next(state.c, state.temperature))
        statsTracker.addProgress(state.c.cost)
        if(state.c.isBetter(state.best)) state = state.changeBest(state.c)
        state = state.changeEqui(state.equi - 1)
      }
      state = state.changeTemperature(cool(state.temperature))
    }

    // Return result
    state.best match {
      case Some(value) => {
        Result(instance.id, instance.setName, value.sumWeight, value.isTrue, value.evaluation, Some(instance.solution), statsTracker)
      }
      case None => Result(instance.id, instance.setName, 0, false, instance.variables.map(v => false), Some(instance.solution), statsTracker)
    }
  }

  def setupSolving(instance: Instance, statsTracker: StatsTracker): SAState = {
    statsTracker.setTimeStart()
    val configuration = Configuration.generateRandomConfiguration(instance)
    SAState(configuration, initTempWeightSum(configuration), None, config.defaultEqui)
  }

}

package solver.simulatedannealing

import model.instance.Configuration.generateRandomConfiguration
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
    i.variables.map(v => v.weight).sum * i.variables.map(v => v.weight).max
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

  def next(i: Configuration, instance: Instance, state: SAState): Configuration = {
    if(state.worseStreak >= config.worseStreakRestartThreshold && Random.nextDouble() > 1 - config.restartChanceWorse) {
      state.worseStreak = 0
      return generateRandomConfiguration(instance)
    }
    if(Random.nextDouble() > 1 - config.restartChance) {
      state.worseStreak = 0
      return generateRandomConfiguration(instance)
    }
    val nextState = i.nextRandom
    if(nextState.isBetter(i)) {
      state.worseStreak = 0
      return nextState
    }

    state.worseStreak += 1
    val delta = i.costDifference(nextState)
    val rnd = new Random()
    if(rnd.nextDouble() < exp(-delta / state.temperature)) {
      return nextState
    }
    i
  }

  override def solve(instance: Instance, statsTracker: StatsTracker): Result = {
    val state: SAState = setupSolving(instance, statsTracker)
    while(!frozenStatic(state.temperature)) {
      state.equi = config.defaultEqui
      while(!equilibrium(state.equi)) {
        state.c = next(state.c, instance, state)
        statsTracker.addVisited(state.c.evaluation)
        statsTracker.addProgress(state.c.cost)
        if(state.c.isBest(state.best)) state.best = Some(state.c)
        state.equi -= 1
      }
      state.temperature = cool(state.temperature)
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

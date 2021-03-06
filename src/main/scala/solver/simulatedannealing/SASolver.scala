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
  val postfixName: String = ""
  override val name: String = s"SA-${config.coolingCoefficient}-${config.defaultEqui}-${config.frozenThreshold}-${config.restartChance}-${config.restartChanceWorse}-${config.initProbability}-${config.tailCutThreshold}-$postfixName"

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

  def findInitTemperature(c: Configuration): Temperature = {
    var temp = config.frozenThreshold + 1
    while(temp < c.maxWeight) {
      val nexts = Seq.fill(c.variables.length * 2)(c.nextRandom).filter(n => !n.isBetter(c))
      val avgChance = nexts.map(n => exp(-calculateDelta(n, c) / temp)).sum / nexts.length
      if(avgChance > config.initProbability) return temp
      else temp *= 1.5
    }
    initTempWeightMax(c)
  }

  def calculateDelta(c: Configuration, next: Configuration): Double = next.costDifference(c)

  def next(i: Configuration, instance: Instance, state: SAState): Configuration = {
    val nextState = i.nextRandom
    if(nextState.isBetter(i)) {
      state.worseStreak = 0
      state.tailStreak = 0
      return nextState
    }

    val delta = calculateDelta(nextState, i)
    val rnd = new Random()
    val chance = exp(-delta / state.temperature)

    if(rnd.nextDouble() < chance) {
      state.tailStreak = 0
      return nextState
    }

    state.tailStreak += 1
    state.worseStreak += 1

    if(Random.nextDouble() > 1 - config.restartChance) {
      return generateRandomConfiguration(instance)
    }

    if(state.worseStreak >= config.worseStreakRestartThreshold &&
      Random.nextDouble() > 1 - config.restartChanceWorse) {
      state.worseStreak = 0
      return generateRandomConfiguration(instance)
    }
    i
  }

  override def solve(instance: Instance, statsTracker: StatsTracker): Result = {
    val state: SAState = setupSolving(instance, statsTracker)
    while(!frozenStatic(state.temperature) && state.tailStreak < config.tailCutThreshold) {
      state.equi = (instance.variables.length * config.defaultEqui).toInt
      while(!equilibrium(state.equi)) {
        state.c = next(state.c, instance, state)
//        statsTracker.addVisited(state.c.evaluation)
//        statsTracker.addProgressBest(state.best)
//        statsTracker.addProgressCost(state.c.cost)
        if(state.c.isBest(state.best)) state.best = Some(state.c)
        state.equi -= 1
      }
      state.temperature = cool(state.temperature)
    }
//    println(s"${!frozenStatic(state.temperature)} vs ${(state.tailStreak < config.tailCutThreshold)}")
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
    SAState(configuration, findInitTemperature(configuration), None, (instance.variables.length * config.defaultEqui).toInt)
  }

}

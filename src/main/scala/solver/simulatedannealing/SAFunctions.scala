package solver.simulatedannealing

import model.instance.{Configuration, Instance}

object SAFunctions {
  def initTempWeightSum(i: Configuration, multiplier: Double = 2): Double = {
    i.variables.map(v => v.weight).sum
  }

  def frozenStatic(T: Double): Boolean = {
    T <= 25
  }

  def equilibrium(i: Int): Boolean = {
    i == 0
  }

  def next(i: Configuration, T: Double): Configuration = {
//    val nextState = i.?nextRandomState()
//    if (nextState better i) return nextState
//    nextState
      i
  }

}

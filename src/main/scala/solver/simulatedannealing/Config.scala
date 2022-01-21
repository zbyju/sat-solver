package solver.simulatedannealing

import solver.simulatedannealing.{InitTemperature,
  CoolingCoefficient, InitEquilibrium, Frozen, Equilibrium, Cool,
  Next, Best, Better}

case class Config(initTemperature: InitTemperature,
                  coolingCoefficient: CoolingCoefficient,
                  initEquilibrium: InitEquilibrium,
                  frozen: Frozen,
                  equilibrium: Equilibrium,
                  cool: Cool,
                  next: Next,
                  best: Best,
                  better: Better,
                  postfix: String)

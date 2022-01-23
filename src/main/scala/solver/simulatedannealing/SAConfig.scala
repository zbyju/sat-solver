package solver.simulatedannealing

case class SAConfig(coolingCoefficient: Double = 0.95,
                    defaultEqui: Int = 20,
                    frozenThreshold: Int = 5,
                    worseStreakRestartThreshold: Int = 100,
                    restartChance: Double = .05,
                    restartChanceWorse: Double = .20)

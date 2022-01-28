package solver.simulatedannealing

case class SAConfig(coolingCoefficient: Double = 0.99,
                    defaultEqui: Int = 10,                      //Should be = n
                    frozenThreshold: Double = 0.1,                 //Should be = 100
                    worseStreakRestartThreshold: Int = 10,
                    restartChance: Double = .1,
                    restartChanceWorse: Double = .20,
                    tailCutThreshold: Int = 10,
                    initProbability: Double = 0.5)

package solver.simulatedannealing

//Fast - 0.9, 10
//Medium - 0.95, 10
//Slow - 0.95, 20

case class SAConfig(coolingCoefficient: Double = 0.95,
                    defaultEqui: Double = 0.5,                      //Should be = n
                    frozenThreshold: Double = 1,                 //Should be = 100
                    worseStreakRestartThreshold: Int = 10,
                    restartChance: Double = .5,
                    restartChanceWorse: Double = .1,
                    tailCutThreshold: Int = 20,
                    initProbability: Double = 0.99)

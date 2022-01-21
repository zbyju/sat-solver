package solver.simulatedannealing

import model.instance.WeightedInstance

type Temperature = Double
type InitTemperature = Any => Temperature
type InitEquilibrium = Any => Int
type CoolingCoefficient = Double

type Frozen = Temperature => Boolean
type Equilibrium = Int => Boolean
type Cool = Temperature => Temperature

type Next = (WeightedInstance, Temperature) => WeightedInstance
type Best = Option[WeightedInstance]
type Better = (WeightedInstance, Best) => Boolean

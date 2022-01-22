package runner

import file.MFileLoader
import model.instance.Instance
import solver.simulatedannealing.{SAConfig, SASolver}
import stats.StatsTracker

object MainRunner {
  val loader = new MFileLoader()
  val solver = new SASolver(new SAConfig(0.9, 40, 25))

  def runInstance(i: Instance, solver: SASolver = solver): Unit = {
    val res = solver.solve(i, StatsTracker())
    println(res)
  }

  def runSet(instances: Seq[Instance], solver: SASolver = solver): Unit = {

  }

  def main(args: Array[String]): Unit = {
    runInstance(loader.instances2078.head)
  }
}

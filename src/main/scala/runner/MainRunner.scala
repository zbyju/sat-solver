package runner

import file.{FileSaver, MFileLoader}
import model.instance.Instance
import model.result.Result
import solver.simulatedannealing.{SAConfig, SASolver}
import stats.StatsTracker

object MainRunner {
  val loader = new MFileLoader()
  val saver = new FileSaver()
  val solver = new SASolver(new SAConfig())

  def runInstance(i: Instance, solver: SASolver = solver): Result = {
    val res = solver.solve(i, StatsTracker())
    print(res.toStringCompare)
    println(s" | ${res.statsTracker.getAverageVisited} / ${res.statsTracker.getMaxVisited} / ${res.statsTracker.getCoverVisited(scala.math.pow(2, i.variables.length).toInt) * 100}")
    res
  }

  def runSet(instances: Seq[Instance], solver: SASolver = solver): Seq[Result] = {
    instances.map(i => runInstance(i, solver))
  }

  def main(args: Array[String]): Unit = {
//    var res = runInstance(loader.instances2078.head)
//    saver.saveProgression(res, "TEST")
//
//    res = runInstance(loader.instances2078(1))
//    saver.saveProgression(res, "TEST2")

    val res = runSet(loader.instances2078)
    saver.saveAverages(res, solver.name)
  }
}

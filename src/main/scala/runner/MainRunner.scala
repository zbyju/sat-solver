package runner

import file.{FileSaver, FileLoader, MFileLoader, NFileLoader, QFileLoader, AFileLoader, RFileLoader}
import model.instance.Instance
import model.result.Result
import solver.simulatedannealing.{SAConfig, SASolver}
import stats.StatsTracker

object MainRunner {
  val mloader = new MFileLoader()
  val nloader = new NFileLoader()
  val aloader = new AFileLoader()
  val qloader = new QFileLoader()
  val rloader = new RFileLoader()
  val saver = new FileSaver()
  val solver = new SASolver(new SAConfig())
  val solverf = new SASolver(new SAConfig(0.9, 0.5, 1, 10, .5, .1, 20, .99))
  val solverm = new SASolver(new SAConfig(0.9, 1, 1, 10, .5, .1, 20, .99))
  val solvers = new SASolver(new SAConfig(0.95, 1, 1, 10, .5, .1, 20, .99))

  def runInstance(i: Instance, solver: SASolver = solver): Result = {
    val res = solver.solve(i, StatsTracker())
    println(res.toStringCompare)
//    println(s" | ${res.statsTracker.getAverageVisited} / ${res.statsTracker.getMaxVisited} / ${res.statsTracker.getCoverVisited(scala.math.pow(2, i.variables.length).toInt) * 100}")
    res
  }

  def runSet(instances: Seq[Instance], solver: SASolver = solver): Seq[Result] = {
    instances.map(i => runInstance(i, solver))
  }

  def runSetOnBoth(loader: FileLoader) = {
    val resf = runSet(loader.instances1, solverf)
    saver.saveFileResults(resf, loader.name1 + "-fast")
    saver.saveAverages(resf, loader.name1 + "-avg-fast")

    val resm = runSet(loader.instances1, solverm)
    saver.saveFileResults(resm, loader.name1 + "-medium")
    saver.saveAverages(resm, loader.name1 + "-avg-medium")

    val ress = runSet(loader.instances1, solvers)
    saver.saveFileResults(ress, loader.name1 + "-slow")
    saver.saveAverages(ress, loader.name1 + "-avg-slow")

    val res2f = runSet(loader.instances2, solverf)
    saver.saveFileResults(res2f, loader.name2 + "-fast")
    saver.saveAverages(res2f, loader.name2 + "-avg-fast")

    val res2m = runSet(loader.instances2, solverm)
    saver.saveFileResults(res2m, loader.name2 + "-medium")
    saver.saveAverages(res2m, loader.name2 + "-avg-medium")

    val res2s = runSet(loader.instances2, solvers)
    saver.saveFileResults(res2s, loader.name2 + "-slow")
    saver.saveAverages(res2s, loader.name2 + "-avg-slow")
  }

  def runM() = runSetOnBoth(mloader)
  def runN() = runSetOnBoth(nloader)
  def runR() = runSetOnBoth(rloader)
  def runQ() = runSetOnBoth(qloader)
  def runA() = runSetOnBoth(aloader)


  def main(args: Array[String]): Unit = {
    runM()
    runN()
    runR()
    runQ()
    runA()
  }
}

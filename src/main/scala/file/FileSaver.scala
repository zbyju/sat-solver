package file

import model.result.Result

import java.io.{BufferedWriter, File, FileWriter}

class FileSaver {

  def saveFileResults(results: Seq[Result], filename: String): Unit = {
    val file = new File("./results/stats/" + filename)
    val bw = new BufferedWriter(new FileWriter(file))
    val timesOutput = results.map(r => r.statsTracker.getTimeNano).mkString(" ")
    val devsOutput = results.map(r => r.getDeviation).mkString(" ")
    val maxVisitedOutput = results.map(r => r.statsTracker.getMaxVisited).mkString(" ")
    val avgVisitedsOutput = results.map(r => r.statsTracker.getAverageVisited).mkString(" ")
    val coverOutput = results.map(r => r.statsTracker.getCoverVisited(scala.math.pow(2, results.head.evaluation.length).toInt)).mkString(" ")
    bw.write(timesOutput)
    bw.write("=\n")
    bw.write(devsOutput)
    bw.write("=\n")
    bw.write(maxVisitedOutput)
    bw.write("=\n")
    bw.write(avgVisitedsOutput)
    bw.write("=\n")
    bw.write(coverOutput)
    bw.write("=\n")
    bw.close()
    println(s"Saved file '$filename'")
  }


  def saveProgression(result: Result, filename: String): Unit = {
    val file = new File("./results/progression/" + filename)
    val bw = new BufferedWriter(new FileWriter(file))
    val progressionOutput = result.statsTracker.getProgressionCost.mkString("", " ", "\n")
    bw.write(progressionOutput)
    bw.write("=\n")
    bw.write(result.statsTracker.getTimeNano.toString)
    bw.close()
    println(s"Saved file '$filename'")
  }

  def getAverage(seq: Seq[Double]): Double = {
    if(seq.isEmpty) return -1
    seq.sum.toDouble / seq.length.toDouble
  }

  def saveAverages(results: Seq[Result], filename: String): Unit = {
    val file = new File("./results/averages/" + filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write("Average Time: " + getAverage(results.map(r => r.statsTracker.getTimeNano / 1000000)) + "\n")
    bw.write("Average Deviation: " + getAverage(results.map(r => r.getDeviation)) + "\n")
    bw.write("Average Cover: " + getAverage(results.map(r => r.statsTracker.getCoverVisited(scala.math.pow(2, results.head.evaluation.length).toInt))) + "\n")
    bw.write("Average Visitation: " + getAverage(results.map(r => r.statsTracker.getAverageVisited)) + "\n")
    bw.close()
    println(s"Saved file '$filename'")
  }
}

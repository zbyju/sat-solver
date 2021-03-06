package file

import model.instance.Instance
import parser.MWCFNParser

class RFileLoader extends FileLoader {
  override val name = "R"
  val parser = new MWCFNParser()

  override val name1 = "R-20-78"
  override val name2 = "R-50-201"

  val path2078 = "/wuf20-78-R/"
  val path50201 = "/wuf50-201-R/"

  val pathSol2078 = "/optimal/wuf20-78-R-opt.dat"
  val pathSol50201 = "/optimal/wuf50-201-R-opt.dat"

  val filenames2078: Seq[FilenamesWithId] = getListOfFiles(path2078).map(mapToFilenameWithId).sortBy(sortFilenamesBy)
  val filenames50201: Seq[FilenamesWithId] = getListOfFiles(path50201).map(mapToFilenameWithId).sortBy(sortFilenamesBy)

  val solutionLines2078: Seq[SolutionWithId] = getInputLines(getInputFile(pathSol2078)).toSeq.map(mapToSolutionWithId).sortBy(sortSolutionLinesBy)
  val solutionLines50201: Seq[SolutionWithId] = getInputLines(getInputFile(pathSol50201)).toSeq.map(mapToSolutionWithId).sortBy(sortSolutionLinesBy)

  override val instances1: Seq[Instance] = createInstances(filenames2078, solutionLines2078, name1)
  override val instances2: Seq[Instance] = createInstances(filenames50201, solutionLines50201, name2)

  def createInstances(filenames: Seq[FilenamesWithId], solutionLines: Seq[SolutionWithId], name: String): Seq[Instance] = {
    filenames.map(f => {
      val sol = solutionLines.find(s => s._1 == f._1)
      sol match {
        case Some(s) => parser.parse(getInputLines(getInputFile(f._2)).toSeq, s, f._1, name)
        case None => null
      }
    }).filter(i => i != null)
  }

  def mapToSolutionWithId(s: String): SolutionWithId = {
    val indexL = s.indexOf("-")
    val indexR = s.indexOf(" ")
    (s.drop(indexL + 1).dropRight(s.length - indexR).toInt, s)
  }

  def mapToFilenameWithId(s: String): SolutionWithId = {
    val indexL = s.lastIndexOf("-")
    val indexR = s.indexOf(".")
    (s.drop(indexL + 1).dropRight(s.length - indexR).toInt, s)
  }

  def sortFilenamesBy(s: FilenamesWithId): Int = s._1

  def sortSolutionLinesBy(s: SolutionWithId): Int = s._1
}

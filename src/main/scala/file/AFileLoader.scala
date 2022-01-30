package file

import model.instance.Instance
import parser.MWCFNParser

class AFileLoader extends FileLoader {
  override val name = "M"
  val parser = new MWCFNParser()

  override val name1 = "A-20-88"
  override val name2 = "A-50-91"

  val path1 = "/wuf20-88-A/"
  val path2 = "/wuf20-91-A/"

  val pathSol1 = "/optimal/wuf20-88-A-opt.dat"
  val pathSol2 = "/optimal/wuf20-91-A-opt.dat"

  val filenames1: Seq[FilenamesWithId] = getListOfFiles(path1).map(mapToFilenameWithId).sortBy(sortFilenamesBy)
  val filenames2: Seq[FilenamesWithId] = getListOfFiles(path2).map(mapToFilenameWithId).sortBy(sortFilenamesBy)

  val solutionLines1: Seq[SolutionWithId] = getInputLines(getInputFile(pathSol1)).toSeq.map(mapToSolutionWithId).sortBy(sortSolutionLinesBy)
  val solutionLines2: Seq[SolutionWithId] = getInputLines(getInputFile(pathSol2)).toSeq.map(mapToSolutionWithId).sortBy(sortSolutionLinesBy)

  override val instances1: Seq[Instance] = createInstances(filenames1, solutionLines1, name1)
  override val instances2: Seq[Instance] = createInstances(filenames2, solutionLines2, name2)

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
    val indexL = s.lastIndexOf("-", s.lastIndexOf("-") - 1)
    val indexR = s.lastIndexOf("-")
    (s.drop(indexL + 1).dropRight(s.length - indexR).toInt, s)
  }

  def sortFilenamesBy(s: FilenamesWithId): Int = s._1

  def sortSolutionLinesBy(s: SolutionWithId): Int = s._1
}

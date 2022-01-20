package file

import model.instance.WeightedInstance
import parser.MWCFNParser

class MFileLoader extends FileLoader {
  val parser = new MWCFNParser()

  val name2078 = "M-20-78"
  val name50201 = "M-50-201"
  val name75310 = "M-75-310"

  val path2078 = "/wuf20-78-M/"
  val path50201 = "/wuf50-201-M/"
  val path75310 = "/wuf75-310-M/"

  val filenames2078: Seq[String] = getListOfFiles(path2078).sortBy(sortFilenamesBy)
  val filenames50201: Seq[String] = getListOfFiles(path50201).sorted
  val filenames75310: Seq[String] = getListOfFiles(path75310).sorted

  val instances2078: Seq[WeightedInstance] = createInstances(filenames2078, name2078)
  val instances50201: Seq[WeightedInstance] = createInstances(filenames50201, name50201)
  val instances75310: Seq[WeightedInstance] = createInstances(filenames75310, name75310)

  def createInstances(filenames: Seq[String], name: String): Seq[WeightedInstance] = {
    filenames.zipWithIndex.map(f => parser.parse(getInputLines(getInputFile(f._1)).toSeq, f._2, name))
  }

  def sortFilenamesBy(s: String): Int = {
    val indexL = s.lastIndexOf("-")
    val indexR = s.indexOf(".")
    s.drop(indexL + 1).dropRight(s.length - indexR).toInt
  }
}

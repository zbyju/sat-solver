package file

import model.instance.Instance

import java.io.File
import scala.io.{BufferedSource, Source}

type SolutionWithId = (Int, String)
type FilenamesWithId = (Int, String)

class FileLoader {
  val name: String = "F"
  val name1: String = "1"
  val name2: String = "2"

  val instances1: Seq[Instance] = Seq()
  val instances2: Seq[Instance] = Seq()

  def getInputFile(path: String): BufferedSource = {
    Source.fromURL(getClass.getResource(path))
  }

  def getInputLines(bufferedSource: BufferedSource): Iterator[String] = {
    bufferedSource.getLines()
  }

  def getListOfFiles(dir: String): List[String] = {
    val file = new File(getClass.getResource(dir).getPath)
    file.listFiles.filter(_.isFile).map(dir + _.getName).toList
  }
}

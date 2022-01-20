package file

import java.io.File
import scala.io.{BufferedSource, Source}

class FileLoader {
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

package runner

import file.MFileLoader

object MainRunner {
  val loader = new MFileLoader()

  def main(args: Array[String]): Unit = {
    println(loader.filenames2078.length)
    println(loader.solutionLines2078.length)
    println(loader.instances2078.length)
  }
}

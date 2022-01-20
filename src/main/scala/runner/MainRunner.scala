package runner

import file.MFileLoader

object MainRunner {
  val loader = new MFileLoader()

  def main(args: Array[String]): Unit = {
    println(loader.instances2078)
  }
}

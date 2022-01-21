package parser

import model.instance.Instance

trait Parser {
  def parse(lines: Seq[String], solutionString: (Int, String), id: Int, setName: String): Instance
}

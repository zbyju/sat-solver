package parser

import model.instance.Instance

trait Parser {
  def parse(lines: Seq[String], id: Int, setName: String): Instance
}

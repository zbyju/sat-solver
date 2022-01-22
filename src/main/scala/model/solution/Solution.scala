package model.solution

class Solution(val id: Int, val setName: String, val sum: Int, val evaluation: Seq[Boolean]) {
  override def toString = s"Sol $setName($id) - $sum - (${evaluation.map(v => if(v) 1 else 0).mkString(", ")})"
}

object Solution {
  def createSolution(line: String, id: Int, setName: String): Solution = {
    val split = line.split(" ").filter(l => l.nonEmpty)
    val sum = split(1).toInt
    val valuation = split.drop(2).map(s => {
      val num = s.toInt
      if(num < 0) false else true
    })
    new Solution(id, setName, sum, valuation)
  }
}

package model.solution

class WeightedSolution(override val id: Int, override val setName: String, val sum: Int, override val valuation: Seq[Boolean]) extends Solution(id, setName, valuation) {
  override def toString = s"Sol $setName($id) - $sum - (${valuation.map(v => if(v) 1 else 0).mkString(", ")})"
}

object WeightedSolution {
  def createSolution(line: String, id: Int, setName: String): WeightedSolution = {
    val split = line.split(" ").filter(l => l.nonEmpty)
    val sum = split(1).toInt
    val valuation = split.drop(2).map(s => {
      val num = s.toInt
      if(num < 0) false else true
    })
    new WeightedSolution(id, setName, sum, valuation)
  }
}

package parser

import model.clause.Clause
import model.instance.{Instance, WeightedInstance}
import model.solution.{Solution, WeightedSolution}
import model.variable.{Variable, WeightedVariable}

class MWCFNParser extends Parser {
  override def parse(lines: Seq[String], solutionWithId: (Int, String), id: Int, setName: String): WeightedInstance = {
    if(id != solutionWithId._1) println("ERROR")

    val pline = lines.find(l => l.startsWith("p")).get
    val wline = lines.find(l => l.startsWith("w")).get
    val wlineIndex = lines.indexOf(wline)
    val clauseLines = lines.filter(l => l.nonEmpty && !l(0).isLetter && l != "%" && l != "0")

    val plineSplit = pline.split(" ")
    val numberOfVars = plineSplit(2).toInt
    val numberOfClauses = plineSplit(3).toInt

    val weights = wline.split(" ").drop(1).map(s => s.toInt)
    val variables = weights.zipWithIndex.map(w => new WeightedVariable(w._2, w._1))

    val clauses = clauseLines.map(c => Clause.createClause(c, variables))

    val solution = WeightedSolution.createSolution(solutionWithId._2, id, setName)

    new WeightedInstance(id, setName, variables, clauses, solution)
  }
}

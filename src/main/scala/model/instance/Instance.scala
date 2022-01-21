package model.instance

import model.variable.Variable
import model.clause.Clause
import model.`type`.Bool
import model.solution.Solution

class Instance(id: Int, setName: String, variables: Seq[Variable], clauses: Seq[Clause], solution: Solution) {
  def solve(): Bool = clauses.map(c => c.value).forall(c => c.value)


  override def toString = s"\nI ($setName - $id) - $variables: ${clauses.mkString("\n", "\n * ", "\n")} (sol: $solution)\n"
}

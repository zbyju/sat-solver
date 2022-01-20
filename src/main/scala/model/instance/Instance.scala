package model.instance

import model.variable.Variable
import model.clause.Clause
import model.`type`.Bool

class Instance(id: Int, setName: String, variables: Seq[Variable], clauses: Seq[Clause]) {
  def solve(): Bool = clauses.map(c => c.value).forall(c => c.value)


  override def toString = s"I ($setName - $id) - $variables: ${clauses.mkString("\n", "\n * ", "\n")}"
}

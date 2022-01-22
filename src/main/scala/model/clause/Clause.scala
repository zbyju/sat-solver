package model.clause

import model.variable.Variable
import model.clause.Clause

class Clause(val variables: Seq[Variable],
             val negations: Seq[Boolean]) {
  lazy val sumWeight: Int = variables.filter(v => v.value).map(v => v.weight).sum
}

object Clause {
  def createClause(line: String, variables: Seq[Variable]): Clause = {
    val numbers = line.split(" ").filter(c => c.nonEmpty).map(s => s.toInt)
    val clauseVars = numbers.filter(n => n != 0).map(n => variables(Math.abs(n) - 1))
    val negations = numbers.map(n => n < 0)
    new Clause(
      clauseVars,
      negations
    )
  }
}
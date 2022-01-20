package model.clause

import model.clause.Clause
import model.`type`.Bool
import model.variable.Variable

class Clause(val variables: Seq[Variable], val negations: Seq[Boolean]) {
  lazy val values: Seq[Bool] = variables.zip(negations).map(v => if(v._2) !v._1 else v._1)

  lazy val value: Bool = values.exists(v => v.value)

  override def toString: String = variables.zip(negations).map(v => (if(v._2) "-" else "") + "x" + v._1.index).mkString("(", " + ", ")")
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

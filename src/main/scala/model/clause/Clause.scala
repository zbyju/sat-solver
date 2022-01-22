package model.clause

import model.`type`.Bool
import model.variable.Variable
import model.clause.Clause
import io.AnsiColor._

class Clause(val variableIndices: Seq[Int],
             val negations: Seq[Boolean]) {
  def variables(variables: Seq[Variable]): Seq[Variable] = variableIndices.map(i => variables.find(v => v.index == i).get)
  def values(vars: Seq[Variable]): Seq[Bool] = variables(vars).zip(negations).map(v => if(v._2) !v._1.value else v._1.value)
  def value(vars: Seq[Variable]): Bool = values(vars).exists(v => v.value)
  def isTrue(vars: Seq[Variable]): Boolean = value(vars).value
  def sumWeight(vars: Seq[Variable]): Int = variables(vars).filter(v => v.value).map(v => v.weight).sum


  override def toString = variableIndices.zip(negations).map(f=>if(f._2) "!"+f._1 else f._1).mkString(s"${YELLOW}C${RESET}(", "+", ")")
}

object Clause {
  def createClause(line: String, variables: Seq[Variable]): Clause = {
    val numbers = line.split(" ").filter(c => c.nonEmpty).map(s => s.toInt)
    val clauseVars = numbers.filter(n => n != 0).map(n => Math.abs(n) - 1)
    val negations = numbers.map(n => n < 0)
    new Clause(
      clauseVars,
      negations
    )
  }
}
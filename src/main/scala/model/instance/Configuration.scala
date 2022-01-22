package model.instance

import model.clause.Clause
import model.variable.Variable

import scala.util.Random
import io.AnsiColor._

class Configuration(val variables: Seq[Variable],
                    val clauses: Seq[Clause]) {

  lazy val sumWeight: Int = variables.filter(v => v.value).map(v => v.weight).sum
  lazy val clauseValues: Seq[Boolean] = clauses.map(c => c.value(variables))
  lazy val isTrue: Boolean = clauseValues.forall(p => p)
  lazy val evaluation: Seq[Boolean] = variables.map(v => v.value)

  lazy val cost: Int = {
    sumWeight
  }

  def isBetter(other: Configuration): Boolean = cost >= other.cost

  def isBetter(other: Option[Configuration]): Boolean = other match {
    case Some(o) => this.isBetter(o)
    case None => true
  }

  def costDifference(other: Configuration): Int = Math.abs(cost - other.cost)

  lazy val nextOneRandom: Configuration = {
    val rnd = new Random()
    val randomIndex = rnd.nextInt(variables.length)
    new Configuration(variables.updated(randomIndex, !variables(randomIndex)), clauses)
  }

  override def toString = s"${RED}CONF: ${RESET}$variables \n $clauses"
}

object Configuration {
  def generateRandomConfiguration(i: Instance): Configuration = {
    val newVars = i.variables.map(v => v.changeValue(Random.nextBoolean()))
    new Configuration(newVars, i.clauses)
  }
}
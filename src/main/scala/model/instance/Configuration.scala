package model.instance

import model.clause.Clause
import model.variable.Variable

import scala.util.Random
import io.AnsiColor._

class Configuration(val variables: Seq[Variable],
                    val clauses: Seq[Clause]) {

  lazy val sumWeight: Int = variables.filter(v => v.value).map(v => v.weight).sum
  lazy val maxWeight: Int = variables.map(v => v.weight).sum
  lazy val clauseValues: Seq[Boolean] = clauses.map(c => c.value(variables))
  lazy val isTrue: Boolean = clauseValues.forall(p => p)
  lazy val evaluation: Seq[Boolean] = variables.map(v => v.value)
  lazy val falseClauses: Seq[Clause] = clauses.filter(c => !c.isTrue(variables))

  lazy val cost: Int = {
    sumWeight
  }

  lazy val cost2: Int = {
    if(isTrue) sumWeight else sumWeight - maxWeight
  }

  def isBest(best: Option[Configuration]): Boolean = best match {
    case Some(b) => {
      if(isTrue && !b.isTrue) return true
      if(!isTrue && b.isTrue) return false
      cost > b.cost
    }
    case None => true
  }

  def isBetter(other: Configuration): Boolean = {
    cost2 > other.cost2
  }

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

  lazy val nextRandom: Configuration = {
    val rnd = new Random()
    if(isTrue) {
      val randomIndices = Seq.fill(variables.length / 4)(rnd.nextInt(variables.length))
      new Configuration(variables.map(v => if(randomIndices.contains(v.index)) !v else v), clauses)
    } else {
      val badVars = falseClauses.flatMap(c => c.badVariables(variables))
      val randomIndex = rnd.nextInt(badVars.length)
      new Configuration(variables.updated(badVars(randomIndex), !variables(badVars(randomIndex))), clauses)
    }
  }

  override def toString = s"${RED}CONF: ${RESET}$variables \n $clauses"
}

object Configuration {
  def generateRandomConfiguration(i: Instance): Configuration = {
    val newVars = i.variables.map(v => v.changeValue(Random.nextBoolean()))
    new Configuration(newVars, i.clauses)
  }
}
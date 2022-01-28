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

  lazy val cost: Double = sumWeight.toDouble

  lazy val cost2: Double = if(isTrue) sumWeight else sumWeight - maxWeight

  lazy val cost3: Double = if(isTrue) sumWeight else sumWeight / 20

  lazy val cost4: Double = {
    (clauses.length - falseClauses.length).toDouble / clauses.length + (sumWeight.toDouble / maxWeight)
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
    cost4 > other.cost4
  }

  def isBetter(other: Option[Configuration]): Boolean = other match {
    case Some(o) => this.isBetter(o)
    case None => true
  }

  def costDifference(other: Configuration): Double = Math.abs(cost4 - other.cost4)

  lazy val nextOneRandom: Configuration = {
    val rnd = new Random()
    val randomIndex = rnd.nextInt(variables.length)
    new Configuration(variables.updated(randomIndex, !variables(randomIndex)), clauses)
  }

  def nextFractionRandom(fraction: Int = 3): Configuration = {
    val randomIndices = Seq.fill(scala.math.max(variables.length / fraction, 3))(Random.nextInt(variables.length))
    new Configuration(variables.map(v => if(randomIndices.contains(v.index)) !v else v), clauses)
  }

  def nextNRandom(n: Int = 2): Configuration = {
    val randomIndices = Seq.fill(n)(Random.nextInt(variables.length))
    new Configuration(variables.map(v => if(randomIndices.contains(v.index)) !v else v), clauses)
  }

  lazy val nextRandom: Configuration = {
    val rnd = new Random()
    if(isTrue) {
      nextNRandom(1)
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
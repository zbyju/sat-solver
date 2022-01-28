package model.instance

import model.clause.Clause
import model.solution.Solution
import model.variable.Variable
import model.instance.Instance
import model.instance.Configuration
import io.AnsiColor._

class Instance(val id: Int, val setName: String,
               override val variables: Seq[Variable],
               override val clauses: Seq[Clause],
               val solution: Solution)
                      extends Configuration(variables, clauses) {

  override def toString = s"${GREEN}$setName (id=$id}):${RESET} $variables \n $clauses \n $solution"
}

object Instance {
  def normalizeWeight(w: Int, min: Int, max: Int): Double = (w - min).toDouble / (max - min)
  def createConfiguration(i: Instance): Configuration = {
    val minWeight = i.variables.map(v => v.weight).min
    val maxWeight = i.variables.map(v => v.weight).max
    new Configuration(i.variables.map(v => {
      new Variable(v.index,
        (normalizeWeight(v.weight, minWeight, maxWeight) * 1000).toInt
      )
    }), i.clauses)
  }
}

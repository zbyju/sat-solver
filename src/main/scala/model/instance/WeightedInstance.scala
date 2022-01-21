package model.instance

import model.clause.Clause
import model.solution.WeightedSolution
import model.variable.WeightedVariable

class WeightedInstance(id: Int, setName: String,
                       variables: Seq[WeightedVariable],
                       clauses: Seq[Clause],
                       solution: WeightedSolution) extends Instance(id, setName, variables, clauses, solution) {
}

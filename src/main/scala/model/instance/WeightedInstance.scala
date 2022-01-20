package model.instance

import model.clause.Clause
import model.variable.WeightedVariable

class WeightedInstance(id: Int, setName: String, variables: Seq[WeightedVariable], clauses: Seq[Clause]) extends Instance(id, setName, variables, clauses) {

}

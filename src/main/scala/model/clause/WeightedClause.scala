package model.clause

import model.variable.WeightedVariable
import model.clause.Clause

class WeightedClause(override val variables: Seq[WeightedVariable],
                     override val negations: Seq[Boolean]) extends Clause(variables, negations) {

}

package model.instance

import model.clause.Clause
import model.variable.Variable

class Configuration(val variables: Seq[Variable],
                    val clauses: Seq[Clause]) {

}
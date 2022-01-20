package model.variable

import model.variable.WeightedVariable
import model.variable.Variable

import scala.language.implicitConversions

class WeightedVariable(override val index: Int, val weight: Int, override val value: Boolean = false) extends Variable(index, value) {
  override def toString = s"x$index ($weight) = $value"
}

object WeightedVariable {
  implicit def weightedVariableToVariable(wv: WeightedVariable): Variable = Variable(wv.index, wv.value)
}

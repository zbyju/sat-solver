package model.variable

import model.variable.Variable
import model.`type`.Bool

import scala.language.implicitConversions

class Variable(val index: Int, override val value: Boolean = false) extends Bool(value) {
  override def toString = s"x$index = $value"
}

object Variable {
  implicit def variableToBool(variable: Variable): Bool = Bool(variable.value)
}

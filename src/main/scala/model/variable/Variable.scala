package model.variable

import model.variable.Variable

import scala.language.implicitConversions

class Variable(val index: Int, val weight: Int, val value: Boolean = false) {
  override def toString = s"x$index ($weight) = $value"
}

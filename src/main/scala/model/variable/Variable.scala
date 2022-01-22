package model.variable

import io.AnsiColor._
import model.variable.Variable

class Variable(val index: Int, val weight: Int, val value: Boolean = false) {
  def unary_! = new Variable(index, weight, !value)
  def changeValue(newValue: Boolean): Variable = new Variable(index, weight, newValue)

  def valueInt: Int = if(value) 1 else 0

  override def toString = s"${BOLD}x$index${RESET}(W=$weight)=$valueInt"
}

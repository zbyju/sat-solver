package model.`type`

import model.`type`.Bool

import scala.language.implicitConversions

class Bool(val value: Boolean) {
  def -(): Bool = !this.value
  def +(other: Bool): Bool = this.value || other.value
  def *(other: Bool): Bool = this.value && other.value

  override def toString = s"Bool($value)"
}

object Bool {
  implicit def booleanToBool(boolean: Boolean): Bool = Bool(boolean)
  implicit def boolToBoolean(bool: Bool): Boolean = bool.value
}

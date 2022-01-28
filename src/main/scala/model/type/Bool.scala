package model.`type`

import model.`type`.Bool

import scala.language.implicitConversions

class Bool(val value: Boolean) {
  def -(): Bool = !this.value
  def +(other: Bool): Bool = this.value || other.value
  def *(other: Bool): Bool = this.value && other.value


  override def toString = s"Bool($value)"

  override def equals(other: Any): Boolean = other match {
    case that: Bool => that.value == this.value
    case that: Boolean => that == this.value
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(value)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Bool {
  implicit def booleanToBool(boolean: Boolean): Bool = Bool(boolean)
  implicit def boolToBoolean(bool: Bool): Boolean = bool.value
}

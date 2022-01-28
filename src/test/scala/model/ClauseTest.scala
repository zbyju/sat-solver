package model

import model.clause.Clause
import model.variable.Variable
import model.`type`.Bool
import org.scalatest.funsuite.AnyFunSuite

class ClauseTest extends AnyFunSuite {
  // Test for (x1 + x2)
  val c1: Clause = Clause(Seq(0, 1), Seq(false, false))
  test("True clause") {
    val var11 = Seq(Variable(0, 1, true), Variable(1, 2, true))
    assert(c1.variables(var11) == var11)
    assert(c1.values(var11) == List(true, true))
    assert(c1.value(var11))
    assert(c1.isTrue(var11))
    assert(c1.sumWeight(var11) == var11(0).weight + var11(1).weight)
    assert(c1.badVariables(var11) == Seq())
  }

  test("One false variable") {
    val var10 = Seq(Variable(0, 1, true), Variable(1, 2, false))
    assert(c1.variables(var10) == var10)
    assert(c1.values(var10) == List(true, false))
    assert(c1.value(var10))
    assert(c1.isTrue(var10))
    assert(c1.sumWeight(var10) == var10(0).weight)
    assert(c1.badVariables(var10) == Seq(1))
  }

  test("Both false") {
    val var00 = Seq(Variable(0, 1, false), Variable(1, 2, false))
    assert(c1.variables(var00) == var00)
    assert(c1.values(var00) == List(false, false))
    assert(!c1.value(var00))
    assert(!c1.isTrue(var00))
    assert(c1.sumWeight(var00) == 0)
    assert(c1.badVariables(var00) == Seq(0, 1))
  }

  //Test for (x1 + -x2)
  val c2: Clause = Clause(Seq(0, 1), Seq(false, true))

  test("True clause with negation") {
    val var10 = Seq(Variable(0, 1, true), Variable(1, 2, false))
    assert(c2.variables(var10) == var10)
    assert(c2.values(var10) == List(true, true))
    assert(c2.value(var10))
    assert(c2.isTrue(var10))
    assert(c2.sumWeight(var10) == var10(0).weight)
    assert(c2.badVariables(var10) == Seq())
  }

  test("One false variable with negation") {
    val var11 = Seq(Variable(0, 1, true), Variable(1, 2, true))
    assert(c2.variables(var11) == var11)
    assert(c2.values(var11) == List(true, false))
    assert(c2.value(var11))
    assert(c2.isTrue(var11))
    assert(c2.sumWeight(var11) == var11(0).weight + var11(1).weight)
    assert(c2.badVariables(var11) == Seq(1))
  }

  test("Both false with negation") {
    val var01 = Seq(Variable(0, 1, false), Variable(1, 2, true))
    assert(c2.variables(var01) == var01)
    assert(c2.values(var01) == List(false, false))
    assert(!c2.value(var01))
    assert(!c2.isTrue(var01))
    assert(c2.sumWeight(var01) == var01(1).weight)
    assert(c2.badVariables(var01) == Seq(0, 1))
  }

}

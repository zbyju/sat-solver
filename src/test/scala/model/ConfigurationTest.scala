package model

import model.clause.Clause
import model.variable.Variable
import model.instance.Configuration
import org.scalatest.funsuite.AnyFunSuite

class ConfigurationTest extends AnyFunSuite{

  // Formula: (x0 + x1) * (x0 + -x1)
  val c1: Clause = Clause(Seq(0, 1), Seq(false, false))
  val c2: Clause = Clause(Seq(0, 1), Seq(false, true))

  test("Test both clauses true") {
    val v1 = Variable(0, 1, true)
    val v2 = Variable(1, 2, false)
    val f = Configuration(Seq(v1, v2), Seq(c1, c2))
    assert(f.sumWeight == v1.weight)
    assert(f.maxWeight == v1.weight + v2.weight)
    assert(f.clauseValues == Seq(true, true))
    assert(f.isTrue)
    assert(f.evaluation == Seq(true, false))
    assert(f.falseClauses == Seq())
    assert(f.cost == v1.weight)
    assert(f.cost2 == v1.weight)
    assert(f.cost3 == v1.weight)
    assert(f.cost4 == (2/2) + (v1.weight.toDouble / (v1.weight + v2.weight)))
  }

  // Formula: (x0 + x1) * (x0 + -x1)
  test("One clause false") {
    val v1 = Variable(0, 1, false)
    val v2 = Variable(1, 2, true)
    val f = Configuration(Seq(v1, v2), Seq(c1, c2))
    assert(f.sumWeight == v2.weight)
    assert(f.maxWeight == v1.weight + v2.weight)
    assert(f.clauseValues == Seq(true, false))
    assert(!f.isTrue)
    assert(f.evaluation == Seq(false, true))
    assert(f.falseClauses == Seq(c2))
    assert(f.cost == v2.weight)
    assert(f.cost >= f.cost2)
    assert(f.cost >= f.cost3)
    assert( f.cost4 == 0.5 + v2.weight.toDouble / (v1.weight + v2.weight) )
  }
  // Formula: (x2 + x2) * (-x0 + x1)
  val c3: Clause = Clause(Seq(2, 2), Seq(false, false))
  val c4: Clause = Clause(Seq(0, 1), Seq(true, false))
  test("Bad vars") {
    val v0 = Variable(0, 1, true)
    val v1 = Variable(1, 2, false)
    val v2 = Variable(2, 3, true)
    val f = Configuration(Seq(v0, v1, v2), Seq(c3, c4))

    assert(f.falseClauses == Seq(c4))

    val badVars = f.falseClauses.flatMap(c => c.badVariables(f.variables))
    assert(badVars == Seq(0, 1))
  }

}

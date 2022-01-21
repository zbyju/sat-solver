package solver

import model.instance.Instance
import model.result.Result

trait Solver {
  val name: String
  def solve(instance: Instance): Result
}

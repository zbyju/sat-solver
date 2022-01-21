package solver

import model.instance.Instance
import model.result.Result

trait Solver {
  def solve(instance: Instance): Result
}

package solver
import model.instance.{Instance, WeightedInstance}
import model.result.WeightedResult

trait WeightedSolver extends Solver {
  def solve(instance: WeightedInstance): WeightedResult
}

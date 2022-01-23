package solver.simulatedannealing

import model.instance.Configuration

case class SAState(var c: Configuration, var temperature: Temperature, var best: Best = None, var equi: Int) {
  var worseStreak: Int = 0
}

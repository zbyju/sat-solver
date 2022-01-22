package solver.simulatedannealing

import model.instance.Configuration

case class SAState(c: Configuration, temperature: Temperature, best: Best = None, equi: Int) {
  def changeConfiguration(newConf: Configuration): SAState = SAState(newConf, temperature, best, equi)
  def changeTemperature(T: Temperature): SAState = SAState(c, T, best, equi)
  def changeBest(newBest: Best): SAState = SAState(c, temperature, newBest, equi)
  def changeBest(newBest: Configuration): SAState = SAState(c, temperature, Some(newBest), equi)
  def changeEqui(newEqui: Int): SAState = SAState(c, temperature, best, newEqui)
}

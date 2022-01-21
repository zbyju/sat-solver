package model.result

import stats.StatsTracker

class WeightedResult(val sum: Int, isSolution: Boolean,
                     override val valuation: Seq[Boolean], override val statsTracker: StatsTracker) extends Result(valuation, statsTracker) {

}

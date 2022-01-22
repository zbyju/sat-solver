package model.result

import stats.StatsTracker

class Result(val sum: Int, isSolution: Boolean,
             val valuation: Seq[Boolean], val statsTracker: StatsTracker) {
  statsTracker.setTimeStartIfNotSet()
}

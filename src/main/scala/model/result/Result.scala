package model.result

import stats.StatsTracker

class Result (val valuation: Seq[Boolean], val statsTracker: StatsTracker){
  statsTracker.setTimeEnd()
}

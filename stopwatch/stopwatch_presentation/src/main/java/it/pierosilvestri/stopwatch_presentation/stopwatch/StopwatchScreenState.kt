package it.pierosilvestri.stopwatch_presentation.stopwatch

import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import kotlin.time.Duration


data class StopwatchScreenState(
    var minutes: String = "00",
    var seconds: String = "00",
    var centiseconds: String = "00",
    var duration: Duration = Duration.ZERO,
    var stopwatchState: StopwatchState = StopwatchState.Idle
)

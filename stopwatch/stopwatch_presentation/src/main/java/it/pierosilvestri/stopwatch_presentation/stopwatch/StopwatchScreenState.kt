package it.pierosilvestri.stopwatch_presentation.stopwatch

import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import kotlin.time.Duration


data class StopwatchScreenState(
    var isLoading: Boolean = false,
    var minutes: String = "00",
    var seconds: String = "00",
    var centiseconds: String = "00",
    var duration: Duration = Duration.ZERO,
    var stopwatchState: StopwatchState = StopwatchState.Idle,
    var player: Player? = null,
    var session: Session? = null,
    var laps: List<Lap> = emptyList()
)

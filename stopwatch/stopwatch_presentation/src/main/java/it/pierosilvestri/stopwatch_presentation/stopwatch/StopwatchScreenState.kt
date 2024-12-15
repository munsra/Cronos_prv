package it.pierosilvestri.stopwatch_presentation.stopwatch

import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.util.UiText
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState


/**
 * This is the state of the stopwatch screen.
 * Here are all the mutable variables that can update the UI.
 */
data class StopwatchScreenState(
    var isLoading: Boolean = false,
    var minutes: String = "00",
    var seconds: String = "00",
    var centiseconds: String = "00",
    var time: Long = 0L,
    var stopwatchState: StopwatchState = StopwatchState.Idle,
    var player: Player? = null,
    var session: Session? = null,
    val error: UiText? = null,
    val isConfirmCancelSessionDialogVisible: Boolean = false,
    val isConfirmSaveSessionDialogVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val confirmMessage: UiText? = null,
)

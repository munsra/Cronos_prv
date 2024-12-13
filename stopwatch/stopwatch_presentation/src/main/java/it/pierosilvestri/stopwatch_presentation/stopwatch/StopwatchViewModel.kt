package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.lifecycle.ViewModel
import it.pierosilvestri.stopwatch_domain.services.StopwatchSimpleService

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class StopwatchViewModel(
    private val stopwatchService: StopwatchSimpleService
) : ViewModel() {

    private val _state = MutableStateFlow(StopwatchScreenState())
    val state = _state.asStateFlow()

    init {
        stopwatchService.observeTime { m, s, cs, state ->
            _state.update {
                it.copy(
                    minutes = m,
                    seconds = s,
                    centiseconds = cs,
                    stopwatchState = state
                )
            }
        }
    }

    fun onAction(action: StopwatchAction) {
        when (action) {
            is StopwatchAction.OnStart -> {
                stopwatchService.startStopwatch()
            }

            is StopwatchAction.OnStop -> {
                stopwatchService.stopStopwatch()
            }

            is StopwatchAction.OnResume -> {

            }
            is StopwatchAction.OnReset -> {
                stopwatchService.resetStopwatch()
            }
        }
    }
}
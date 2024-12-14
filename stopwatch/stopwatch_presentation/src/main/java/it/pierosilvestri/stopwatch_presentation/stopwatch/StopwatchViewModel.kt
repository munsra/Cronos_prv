package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import it.pierosilvestri.stopwatch_domain.services.StopwatchSimpleService

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StopwatchViewModel(
    private val stopwatchService: StopwatchSimpleService,
    private val sessionRepository: SessionRepository,
    private val playerRepository: PlayerRepository
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

    fun loadData(playerId: String, sessionId: String) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            playerRepository.getPlayer(playerId)?.let { player ->
                val session = sessionRepository.getSession(sessionId)
                val laps = session?.laps
                _state.update {
                    it.copy(
                        player = player,
                        session = session,
                        laps = laps ?: emptyList(),
                        isLoading = false
                    )
                }
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
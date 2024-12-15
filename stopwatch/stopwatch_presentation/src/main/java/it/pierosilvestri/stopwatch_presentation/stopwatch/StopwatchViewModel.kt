package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.repository.LapRepository
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import it.pierosilvestri.stopwatch_domain.services.StopwatchService

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StopwatchViewModel(
    private val stopwatchService: StopwatchService,
    private val sessionRepository: SessionRepository,
    private val playerRepository: PlayerRepository,
    private val lapRepository: LapRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StopwatchScreenState())
    val state = _state.asStateFlow()

    init {
        stopwatchService.observeTime { m, s, cs, state ->
            val totalCentiseconds = toCentiseconds(m, s, cs)
            _state.update {
                it.copy(
                    minutes = m,
                    seconds = s,
                    centiseconds = cs,
                    time = totalCentiseconds,
                    stopwatchState = state
                )
            }
        }
    }

    /**
     * Loads the data of the player and the session from the repository
     */
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

    /**
     * Handles the actions of the UI
     */
    fun onAction(action: StopwatchAction) {
        when (action) {
            is StopwatchAction.OnStart -> {
                stopwatchService.startStopwatch()
            }

            is StopwatchAction.OnStop -> {
                stopwatchService.stopStopwatch()
            }

            is StopwatchAction.OnResume -> {
                stopwatchService.startStopwatch()
            }

            is StopwatchAction.OnReset -> {
                _state.update {
                    it.copy(
                        laps = emptyList()
                    )
                }
                stopwatchService.resetStopwatch()
            }

            StopwatchAction.OnLap -> {
                val lap = Lap(
                    totalTime = getCurrentLapTimeFromAllLaps(),
                    datetime = System.currentTimeMillis()
                )
                _state.update {
                    it.copy(
                        laps = it.laps + lap
                    )
                }
            }
        }
    }

    /**
     * Returns the value of the lap in centiseconds
     */
    private fun getCurrentLapTimeFromAllLaps(): Long {
        val currentTotalLapTime = _state.value.laps.sumOf { it.totalTime }
        return _state.value.time - currentTotalLapTime
    }


    private fun toCentiseconds(minutes: String, seconds: String, centiseconds: String): Long {
        val totalMinutes = minutes.toLongOrNull() ?: 0
        val totalSeconds = seconds.toLongOrNull() ?: 0
        val totalCentiseconds = centiseconds.toLongOrNull() ?: 0

        return (totalMinutes * 60 * 100) + (totalSeconds * 100) + totalCentiseconds
    }
}
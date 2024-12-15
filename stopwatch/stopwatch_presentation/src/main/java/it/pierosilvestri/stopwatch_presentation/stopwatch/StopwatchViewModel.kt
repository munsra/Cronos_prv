package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import it.pierosilvestri.core.util.UiText
import it.pierosilvestri.stopwatch_domain.services.StopwatchService
import it.pierosilvestri.stopwatch_presentation.R
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StopwatchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val stopwatchService: StopwatchService,
    private val playerRepository: PlayerRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    val playerId = savedStateHandle.get<String>("playerId")
    val sessionId = savedStateHandle.get<String>("sessionId")

    private val _state = MutableStateFlow(StopwatchScreenState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<StopwatchEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
    fun loadData() {
        viewModelScope.launch {
            val player = playerRepository.getPlayer(playerId!!)
            val session = sessionRepository.getSession(sessionId!!)
            _state.update {
                it.copy(
                    player = player,
                    session = session,
                    isLoading = false
                )
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
                        session = it.session!!.copy(
                            laps = emptyList()
                        )
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
                        session = state.value.session!!.copy(
                            laps = it.session!!.laps.plus(lap)
                        ),
                    )
                }
            }

            StopwatchAction.OnBackButtonPressed -> {
                _state.update {
                    it.copy(
                        isConfirmCancelSessionDialogVisible = true,
                        isConfirmSaveSessionDialogVisible = false,
                        confirmMessage = UiText.DynamicString("Are you sure you want to cancel the session?")
                    )
                }
            }
            StopwatchAction.OnSaveButtonPressed -> {
                _state.update {
                    it.copy(
                        isConfirmCancelSessionDialogVisible = false,
                        isConfirmSaveSessionDialogVisible = true,
                        confirmMessage = UiText.StringResource(R.string.confirm_save_session_message)
                    )
                }
            }

            StopwatchAction.OnConfirmDialogConfirm -> {
                if(_state.value.isConfirmSaveSessionDialogVisible){
                    _state.update {
                        it.copy(
                            isConfirmCancelSessionDialogVisible = false,
                            isConfirmSaveSessionDialogVisible = false,
                            confirmMessage = null
                        )
                    }
                    saveSession()
                }else if(_state.value.isConfirmCancelSessionDialogVisible){
                    deleteSession()
                }
            }
            StopwatchAction.OnConfirmDialogDismiss -> {
                _state.update {
                    it.copy(
                        isConfirmCancelSessionDialogVisible = false,
                        isConfirmSaveSessionDialogVisible = false,
                        confirmMessage = null
                    )
                }
            }
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            sessionRepository.deleteSession(_state.value.session!!)
            _uiEvent.send(StopwatchEvent.NavigateBack)
        }
    }

    private fun saveSession() {
        viewModelScope.launch {
            sessionRepository.addSession(_state.value.session!!, _state.value.player!!)
            _uiEvent.send(StopwatchEvent.NavigateBack)
        }
    }

    /**
     * Returns the value of the lap in centiseconds
     */
    private fun getCurrentLapTimeFromAllLaps(): Long {
        val currentTotalLapTime = _state.value.session!!.laps.sumOf { it.totalTime }
        return _state.value.time - currentTotalLapTime
    }


    private fun toCentiseconds(minutes: String, seconds: String, centiseconds: String): Long {
        val totalMinutes = minutes.toLongOrNull() ?: 0
        val totalSeconds = seconds.toLongOrNull() ?: 0
        val totalCentiseconds = centiseconds.toLongOrNull() ?: 0

        return (totalMinutes * 60 * 100) + (totalSeconds * 100) + totalCentiseconds
    }
}
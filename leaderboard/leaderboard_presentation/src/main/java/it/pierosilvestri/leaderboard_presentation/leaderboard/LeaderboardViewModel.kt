package it.pierosilvestri.leaderboard_presentation.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import it.pierosilvestri.core.util.UIEvent
import it.pierosilvestri.leaderboard_domain.use_case.CalculateLeaderboard
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val playerRepository: PlayerRepository,
    private val sessionRepository: SessionRepository,
    private val calculateLeaderboard: CalculateLeaderboard
): ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<LeaderboardEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        /**
         * Get the players from the repository and calculate the leaderboard
         */
        viewModelScope.launch {
            playerRepository.getPlayers().collect{ players ->
                // Calculate the leaderboard based on the players list
                // and update the state with the new leaderboard and players list
                val leaders = calculateLeaderboard(players)
                _state.update {
                    it.copy(
                        leaders = leaders,
                        players = players
                    )
                }
            }
        }
    }

    /**
     * Handles the actions of the UI
     */
    fun onAction(action: LeaderboardAction) {
        when (action) {
            LeaderboardAction.OpenNewSession -> {
                _state.update {
                    it.copy(
                        isNewSessionDialogOpen = true
                    )
                }
            }

            is LeaderboardAction.ConfirmNewSession -> {
                _state.update {
                    it.copy(
                        isNewSessionDialogOpen = false
                    )
                }
                viewModelScope.launch {
                    sessionRepository.addSession(session = action.session, player = action.player)
                    _uiEvent.send(LeaderboardEvent.OpenNextPage(action.player, action.session))
                }
            }
            LeaderboardAction.DismissNewSessionDialog -> {
                _state.update {
                    it.copy(
                        isNewSessionDialogOpen = false
                    )
                }
            }
        }
    }
}
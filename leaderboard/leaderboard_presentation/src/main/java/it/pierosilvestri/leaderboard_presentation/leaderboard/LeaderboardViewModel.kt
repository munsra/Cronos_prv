package it.pierosilvestri.leaderboard_presentation.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val playerRepository: PlayerRepository
): ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            playerRepository.getPlayers().collect{
                _state.value = _state.value.copy(
                    leaders = it
                )
            }
        }
    }

    fun onAction(action: LeaderboardAction) {
        when (action) {
            LeaderboardAction.OpenNewSession -> {
                val player = Player(
                    fullname = "Angus Young",
                    pictures = null,
                    sessions = null
                )
                viewModelScope.launch {
                    playerRepository.addPlayer(player)
                }
            }
        }
    }
}
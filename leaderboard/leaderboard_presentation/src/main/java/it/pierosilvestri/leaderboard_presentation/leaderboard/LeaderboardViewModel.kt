package it.pierosilvestri.leaderboard_presentation.leaderboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LeaderboardViewModel: ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()

    fun onAction(action: LeaderboardAction) {
        when (action) {
            LeaderboardAction.OpenNewSession -> {
                //TODO: Open new session
            }
        }
    }

}
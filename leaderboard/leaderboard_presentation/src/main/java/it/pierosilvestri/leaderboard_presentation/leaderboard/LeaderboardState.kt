package it.pierosilvestri.leaderboard_presentation.leaderboard

import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.leaderboard_domain.use_case.CalculateLeaderboard

data class LeaderboardState(
    val isLoading: Boolean = false,
    val leaders: List<Player> = emptyList(),
    val players: List<Player> = emptyList(),
    val error: String? = null,
    val isNewSessionDialogOpen: Boolean = false
)

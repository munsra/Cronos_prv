package it.pierosilvestri.leaderboard_presentation.leaderboard

import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.util.UiText
import it.pierosilvestri.leaderboard_domain.use_case.CalculateLeaderboard

data class LeaderboardState(
    val isLoading: Boolean = false,
    val leaders: List<Player> = emptyList(),
    val players: List<Player> = emptyList(),
    val errorMessage: UiText? = null,
    val isNewSessionDialogOpen: Boolean = false,
    val isExportToExcelDialogOpen: Boolean = false,
    val loadingMessage: UiText? = null
)

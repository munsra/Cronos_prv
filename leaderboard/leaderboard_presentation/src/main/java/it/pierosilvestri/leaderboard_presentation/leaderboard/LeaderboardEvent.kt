package it.pierosilvestri.leaderboard_presentation.leaderboard

import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session

sealed class LeaderboardEvent {
    data class OpenNextPage(
        val player: Player,
        val session: Session
    ) : LeaderboardEvent()
}
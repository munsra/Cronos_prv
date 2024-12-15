package it.pierosilvestri.leaderboard_presentation.leaderboard

import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session

sealed class LeaderboardAction {
    data object OpenNewSession : LeaderboardAction()
    data object DismissNewSessionDialog : LeaderboardAction()
    data object LoadPlayersFromRemote: LeaderboardAction()
    data class ConfirmNewSession(
        val player: Player,
        val session: Session
    ) : LeaderboardAction()
}
package it.pierosilvestri.leaderboard_presentation.leaderboard

sealed class LeaderboardAction {
    data object OpenNewSession: LeaderboardAction()
}
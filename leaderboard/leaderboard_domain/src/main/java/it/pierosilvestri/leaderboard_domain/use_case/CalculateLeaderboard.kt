package it.pierosilvestri.leaderboard_domain.use_case

import it.pierosilvestri.core.domain.model.Player

/**
 * This class is used to calculate the leaderboard.
 * It takes a list of players and returns a new list of player based on
 * their sessions records.
 * Take the best session from each player and compare them.
 */
class CalculateLeaderboard {

    operator fun invoke(players: List<Player>): List<Player> {
        val list = mutableListOf<Player>()
        for (player in players){
            list.add(player)
        }
        return emptyList()
    }
}
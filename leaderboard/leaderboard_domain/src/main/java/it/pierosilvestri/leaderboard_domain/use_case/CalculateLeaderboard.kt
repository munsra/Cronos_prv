package it.pierosilvestri.leaderboard_domain.use_case

import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session

/**
 * This class is used to calculate the leaderboard.
 * It takes a list of players and returns a new list of player based on
 * their sessions records.
 * Take the best session from each player and compare them.
 */
class CalculateLeaderboard {
    operator fun invoke(players: List<Player>): List<Player> {
        // Sort players based on their best performance based on distance/time.
        return players.sortedByDescending { player ->
            if(player.sessions == null) Long.MAX_VALUE
            player.sessions?.maxOfOrNull { session ->
                if(session.laps.isEmpty()) Long.MIN_VALUE
                // get the best (minor) lap time for the same distance
                session.distance / session.laps.minOf { it.totalTime }
            }
        }
    }
}
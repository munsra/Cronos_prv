package it.pierosilvestri.core.domain.repository

import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result
import it.pierosilvestri.core.domain.model.Player
import kotlinx.coroutines.flow.Flow

/**
 * In this interface I declare the functions for my  Player repository
 * in order to manage databases o mock databases
 */
interface PlayerRepository {
    fun getPlayers(): Flow<List<Player>>
    suspend fun getPlayersFromRemote(): Result<List<Player>, DataError.Remote>
    suspend fun getPlayer(playerId: String): Player?
    suspend fun addPlayer(player: Player)
    suspend fun addPlayers(players: List<Player>)
}
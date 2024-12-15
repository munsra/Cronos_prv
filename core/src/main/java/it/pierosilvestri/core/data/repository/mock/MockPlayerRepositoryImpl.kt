package it.pierosilvestri.core.data.repository.mock

import it.pierosilvestri.core.data.database.MockDatabase
import it.pierosilvestri.core.data.remote.RemotePlayerDataSource
import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result
import it.pierosilvestri.core.domain.map
import it.pierosilvestri.core.domain.mapper.toPlayer
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class MockPlayerRepositoryImpl(
    private val mockDatabase: MockDatabase,
    private val remotePlayerDataSource: RemotePlayerDataSource,
): PlayerRepository {

    override fun getPlayers(): Flow<List<Player>> {
        return mockDatabase.getAllPlayers()
    }

    override suspend fun getPlayersFromRemote(): Result<List<Player>, DataError.Remote> {
        return remotePlayerDataSource
            .getPlayers()
            .map { dto ->
                dto.results.map { it.toPlayer() }
            }
    }

    override suspend fun getPlayer(playerId: String): Player? {
        return mockDatabase.getPlayer(playerId)
    }

    override suspend fun addPlayer(player: Player) {
        mockDatabase.addPlayer(player)
    }
}
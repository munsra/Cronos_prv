package it.pierosilvestri.core.data.repository.mock

import it.pierosilvestri.core.data.database.MockDatabase
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class MockPlayerRepositoryImpl(
    private val mockDatabase: MockDatabase
): PlayerRepository {

    override fun getPlayers(): Flow<List<Player>> {
        return mockDatabase.getAllPlayers()
    }

    override suspend fun getPlayer(playerId: String): Player? {
        return mockDatabase.getPlayer(playerId)
    }

    override suspend fun addPlayer(player: Player) {
        mockDatabase.addPlayer(player)
    }
}
package it.pierosilvestri.core.data.repository.mock

import it.pierosilvestri.core.data.database.MockDatabase
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.domain.repository.LapRepository

class MockLapRepositoryImpl(
    private val mockDatabase: MockDatabase
): LapRepository {
    override suspend fun addLap(lap: Lap, session: Session, player: Player) {
        return mockDatabase.addLap(lap, session, player)
    }
}
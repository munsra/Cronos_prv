package it.pierosilvestri.core.data.repository.mock

import it.pierosilvestri.core.data.database.mock.MockDatabase
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.domain.repository.SessionRepository

class MockSessionRepositoryImpl(
    private val mockDatabase: MockDatabase
): SessionRepository {
    override suspend fun getSession(sessionId: String): Session? {
        return mockDatabase.getSession(sessionId)
    }

    override suspend fun saveSession(session: Session, player: Player) {
        mockDatabase.addSession(session, player)
    }

    override suspend fun deleteSession(session: Session) {
        mockDatabase.deleteSession(session)
    }
}
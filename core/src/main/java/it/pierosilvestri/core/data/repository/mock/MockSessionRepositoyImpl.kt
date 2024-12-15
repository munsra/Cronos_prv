package it.pierosilvestri.core.data.repository.mock

import it.pierosilvestri.core.data.database.MockDatabase
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.domain.repository.SessionRepository

class MockSessionRepositoyImpl(
    private val mockDatabase: MockDatabase
): SessionRepository {
    override suspend fun getSession(sessionId: String): Session? {
        return mockDatabase.getSession(sessionId)
    }

    override fun addSession(session: Session, player: Player) {
        mockDatabase.addSession(session, player)
    }

    override fun deleteSession(session: Session) {
        mockDatabase.deleteSession(session)
    }
}
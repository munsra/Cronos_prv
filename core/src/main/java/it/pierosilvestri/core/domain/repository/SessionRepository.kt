package it.pierosilvestri.core.domain.repository

import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session

interface SessionRepository {
    suspend fun getSession(sessionId: String): Session?
    suspend fun saveSession(session: Session, player: Player)
    suspend fun deleteSession(session: Session)
}
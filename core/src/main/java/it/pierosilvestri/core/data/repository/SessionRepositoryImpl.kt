package it.pierosilvestri.core.data.repository

import it.pierosilvestri.core.data.database.daos.LapDao
import it.pierosilvestri.core.data.database.daos.SessionDao
import it.pierosilvestri.core.domain.mapper.toLap
import it.pierosilvestri.core.domain.mapper.toLapEntity
import it.pierosilvestri.core.domain.mapper.toSession
import it.pierosilvestri.core.domain.mapper.toSessionEntity
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val sessionDao: SessionDao,
    private val lapDao: LapDao
): SessionRepository {
    override suspend fun getSession(sessionId: String): Session? {
        val lapsEntity = lapDao.getLapsBySessionId(sessionId)
        val laps = lapsEntity.map { it.toLap() }
        return sessionDao.getById(sessionId).toSession(laps)
    }

    override suspend fun saveSession(session: Session, player: Player) {
        session.laps.forEach {
            lapDao.upsert(it.toLapEntity(session.id))
        }
        sessionDao.upsert(session.toSessionEntity(player.id))
    }

    override suspend fun deleteSession(session: Session) {
        sessionDao.delete(session.id)
    }
}
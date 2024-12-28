package it.pierosilvestri.core.data.repository

import it.pierosilvestri.core.data.database.daos.LapDao
import it.pierosilvestri.core.domain.mapper.toLapEntity
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.domain.repository.LapRepository

class LapRepositoryImpl(
    private val lapDao: LapDao
): LapRepository {
    override suspend fun addLap(lap: Lap, session: Session, player: Player) {
        lapDao.insert(lap.toLapEntity(session.id))
    }
}
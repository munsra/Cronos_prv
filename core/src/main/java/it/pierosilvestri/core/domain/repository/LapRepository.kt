package it.pierosilvestri.core.domain.repository

import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session

interface LapRepository {
    suspend fun addLap(lap: Lap, session: Session, player: Player)
}
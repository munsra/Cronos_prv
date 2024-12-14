package it.pierosilvestri.core.domain.repository

import it.pierosilvestri.core.domain.model.Session

interface SessionRepository {

    fun insertSession(session: Session)

}
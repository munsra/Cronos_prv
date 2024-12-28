package it.pierosilvestri.core.domain.mapper

import it.pierosilvestri.core.data.database.model.LapEntity
import it.pierosilvestri.core.data.database.model.PlayerEntity
import it.pierosilvestri.core.data.database.model.PlayerPicturesEntity
import it.pierosilvestri.core.data.database.model.SessionEntity
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session
import java.util.UUID

fun Player.toPlayerEntity(): PlayerEntity {
    return PlayerEntity(
        id = id,
        fullname = fullname
    )
}

fun Session.toSessionEntity(playerId: String): SessionEntity {
    return SessionEntity(
        id = id,
        distance = distance,
        startDate = startDate,
        playerId = playerId
    )
}

fun Lap.toLapEntity(sessionId: String): LapEntity {
    return LapEntity(
        id = UUID.randomUUID().toString(),
        totalTime = totalTime,
        datetime = datetime,
        sessionId = sessionId
    )
}

fun PlayerPictures.toPlayerPicturesEntity(playerId: String): PlayerPicturesEntity {
    return PlayerPicturesEntity(
        id = UUID.randomUUID().toString(),
        largePicture = largePicture,
        mediumPicture = mediumPicture,
        smallPicture = smallPicture,
        playerId = playerId
    )
}
package it.pierosilvestri.core.domain.mapper

import it.pierosilvestri.core.data.database.model.LapEntity
import it.pierosilvestri.core.data.database.model.PlayerEntity
import it.pierosilvestri.core.data.database.model.PlayerPicturesEntity
import it.pierosilvestri.core.data.database.model.SessionEntity
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session

fun PlayerEntity.toPlayer(session: List<Session>, pictures: PlayerPictures?): Player {
    return Player (
        id = id,
        fullname = fullname,
        pictures = pictures,
        sessions = session
    )
}

fun SessionEntity.toSession(laps: List<Lap>): Session {
    return Session(
        id = id,
        distance = distance,
        startDate = startDate,
        laps = laps
    )
}

fun LapEntity.toLap(): Lap {
    return Lap(
        totalTime = totalTime,
        datetime = datetime
    )
}

fun PlayerPicturesEntity.toPlayerPictures(): PlayerPictures {
    return PlayerPictures(
        largePicture = largePicture,
        mediumPicture = mediumPicture,
        smallPicture = smallPicture
    )
}
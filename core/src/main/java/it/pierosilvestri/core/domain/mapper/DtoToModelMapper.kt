package it.pierosilvestri.core.domain.mapper

import it.pierosilvestri.core.data.dto.LapDto
import it.pierosilvestri.core.data.dto.PlayerDto
import it.pierosilvestri.core.data.dto.PlayerPicturesDto
import it.pierosilvestri.core.data.dto.SessionDto
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session
import java.util.Calendar
import java.util.Date

/**
 * This is a mapper where I can convert my Dto entities to Model data.
 */
fun PlayerDto.toPlayer(): Player {
    return Player (
        id = id ?: "",
        fullname = fullname.toString(),
        pictures = picture?.toPlayerPictures(),
        sessions = sessions?.map { it.toSession() }
    )
}

fun PlayerPicturesDto.toPlayerPictures(): PlayerPictures {
    return PlayerPictures(
        largePicture = large,
        mediumPicture = medium,
        smallPicture = small
    )
}

fun SessionDto.toSession(): Session {
    return Session (
        id = id,
        distance = distance,
        startDate = 0L, //startDate,
        laps = if(laps == null) emptyList() else laps.map { it.toLap() }
    )
}

fun LapDto.toLap(): Lap {
    return Lap (
        totalTime = totalTime,
        datetime = Calendar.getInstance().time.time
    )
}
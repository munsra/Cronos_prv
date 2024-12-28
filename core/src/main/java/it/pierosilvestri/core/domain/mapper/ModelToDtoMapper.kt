package it.pierosilvestri.core.domain.mapper

import it.pierosilvestri.core.data.dto.LapDto
import it.pierosilvestri.core.data.dto.PlayerDto
import it.pierosilvestri.core.data.dto.PlayerNameDto
import it.pierosilvestri.core.data.dto.PlayerPicturesDto
import it.pierosilvestri.core.data.dto.SessionDto
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session
import java.util.UUID

/**
 * This is a mapper where I can convert my Model data to Dto.
 */
fun Player.toPlayerDto(): PlayerDto {
    return PlayerDto(
        id = id,
        fullname = PlayerNameDto(
            title = fullname.split(" ")[0],
            first = fullname.split(" ")[1],
            last = fullname.split(" ")[2]
        ),
        picture = pictures?.toPlayerPicturesDto(),
        sessions = sessions?.map { it.toSessionDto() }
    )
}

fun PlayerPictures.toPlayerPicturesDto(): PlayerPicturesDto {
    return PlayerPicturesDto(
        large = largePicture,
        medium = mediumPicture,
        small = smallPicture
    )
}

fun Session.toSessionDto(): SessionDto {
    return SessionDto(
        id = id ?: UUID.randomUUID().toString(),
        distance = distance,
        startDate = startDate.toString(),
        laps = laps.map { it.toLapDto() }
    )
}

fun Lap.toLapDto(): LapDto {
    return LapDto(
        totalTime = totalTime
    )
}
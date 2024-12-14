package it.pierosilvestri.core.domain.mapper

import it.pierosilvestri.core.data.dto.LapDto
import it.pierosilvestri.core.data.dto.PlayerDto
import it.pierosilvestri.core.data.dto.PlayerPicturesDto
import it.pierosilvestri.core.data.dto.SessionDto
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session

fun Player.toPlayerDto(): PlayerDto {
    return PlayerDto(
        fullname = fullname,
        pictures = pictures?.toPlayerPicturesDto(),
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
        distance = distance,
        startDate = startDate.toString(),
        laps = laps?.map { it.toLapDto() }
    )
}

fun Lap.toLapDto(): LapDto {
    return LapDto(
        totalTime = totalTime
    )
}
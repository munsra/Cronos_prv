package it.pierosilvestri.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto (
    val id: String? = null,
    @SerialName("name") val fullname: PlayerNameDto,
    val picture: PlayerPicturesDto? = null,
    val sessions: List<SessionDto>? = null
)
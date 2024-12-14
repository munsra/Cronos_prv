package it.pierosilvestri.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto (
    val id: String,
    @SerialName("name") val fullname: String,
    val pictures: PlayerPicturesDto?,
    val sessions: List<SessionDto>?
)
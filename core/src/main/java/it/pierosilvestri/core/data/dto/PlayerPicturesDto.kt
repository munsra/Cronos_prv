package it.pierosilvestri.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerPicturesDto(
    val large: String,
    val medium: String,
    @SerialName("thumbnail") val small: String
)

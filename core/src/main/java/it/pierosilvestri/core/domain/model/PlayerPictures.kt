package it.pierosilvestri.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPictures(
    val largePicture: String? = null,
    val mediumPicture: String? = null,
    val smallPicture: String? = null,
)

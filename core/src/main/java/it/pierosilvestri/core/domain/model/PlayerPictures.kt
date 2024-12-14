package it.pierosilvestri.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPictures(
    val largePicture: String,
    val mediumPicture: String,
    val smallPicture: String,
)

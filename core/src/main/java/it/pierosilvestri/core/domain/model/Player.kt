package it.pierosilvestri.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    var id: String,
    val fullname: String,
    val pictures: PlayerPictures?,
    var sessions: List<Session>?,
)
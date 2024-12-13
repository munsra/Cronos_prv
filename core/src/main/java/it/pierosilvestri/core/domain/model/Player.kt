package it.pierosilvestri.core.domain.model

data class Player(
    val name: String,
    val surname: String,
    val title: String,
    val pictures: List<PlayerPictures>?,
    val sessions: List<Session>?,
)
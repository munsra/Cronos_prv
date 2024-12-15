package it.pierosilvestri.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val id: String,
    val distance: Int,
    val startDate: Long,
    var laps: List<Lap> = emptyList()
)
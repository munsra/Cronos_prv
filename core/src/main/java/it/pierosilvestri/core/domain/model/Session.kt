package it.pierosilvestri.core.domain.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Session(
    val distance: Double,
    val startDate: Long,
    var laps: List<Lap>?
)
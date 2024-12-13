package it.pierosilvestri.core.domain.model

import java.util.Date

data class Session(
    val distance: Double,
    val startDate: Date,
    val endDate: Date,
    val laps: List<Lap>?
)
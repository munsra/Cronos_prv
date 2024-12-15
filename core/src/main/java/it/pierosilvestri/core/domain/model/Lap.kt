package it.pierosilvestri.core.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Lap(
    val totalTime: Long,
    val datetime: Long
)

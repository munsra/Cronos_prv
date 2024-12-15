package it.pierosilvestri.core.data.dto

import it.pierosilvestri.core.data.dto.PlayerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponseDto (
    @SerialName("results") val results: List<PlayerDto>
)
package it.pierosilvestri.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerNameDto(
    val title: String,
    val first: String,
    val last: String
) {
    override fun toString(): String {
        return "$title $first $last"
    }
}
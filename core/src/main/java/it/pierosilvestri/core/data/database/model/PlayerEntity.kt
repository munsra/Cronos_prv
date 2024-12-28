package it.pierosilvestri.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = PlayerEntity.TABLE_NAME,
)
data class PlayerEntity(
    @PrimaryKey val id: String,
    val fullname: String,
) {
    companion object {
        const val TABLE_NAME = "players"
    }
}

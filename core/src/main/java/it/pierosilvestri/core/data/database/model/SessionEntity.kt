package it.pierosilvestri.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = SessionEntity.TABLE_NAME,
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
        )
    ]
)
data class SessionEntity(
    @PrimaryKey val id: String,
    val distance: Int,
    val startDate: Long,
    val playerId: String
) {
    companion object {
        const val TABLE_NAME = "sessions"
    }
}
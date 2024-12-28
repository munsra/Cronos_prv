package it.pierosilvestri.core.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * The LapEntity rappresents the lap of a session.
 * I can have multiple laps per session.
 * The sessionId is the foreign key to the SessionEntity.
 * When the session is deleted, the laps are also deleted.
 */
@Entity(
    tableName = LapEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LapEntity(
    @PrimaryKey val id: String,
    val totalTime: Long,
    val datetime: Long,
    val sessionId: String
) {
    companion object {
        const val TABLE_NAME = "laps"
    }
}
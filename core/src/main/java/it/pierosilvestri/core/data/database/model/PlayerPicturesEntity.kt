package it.pierosilvestri.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = PlayerPicturesEntity.TABLE_NAME,
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
        )
    ]
)
data class PlayerPicturesEntity(
    @PrimaryKey val id: String,
    val largePicture: String?,
    val mediumPicture: String?,
    val smallPicture: String?,
    val playerId: String
) {
    companion object {
        const val TABLE_NAME = "players_pictures"
    }
}

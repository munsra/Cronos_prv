package it.pierosilvestri.core.data.database.daos

import androidx.room.Dao
import androidx.room.Upsert
import androidx.room.Query
import it.pierosilvestri.core.data.database.model.PlayerPicturesEntity

@Dao
interface PlayerPicturesDao {
    @Upsert
    suspend fun upsert(playerPictures: PlayerPicturesEntity)

    @Query("SELECT * FROM players_pictures WHERE playerId = :playerId")
    suspend fun getPlayerPicturesByPlayerId(playerId: String): PlayerPicturesEntity

    @Query("DELETE FROM players_pictures WHERE playerId = :playerId")
    suspend fun deletePlayerPicturesByPlayerId(playerId: String)

}
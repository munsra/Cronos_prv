package it.pierosilvestri.core.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import it.pierosilvestri.core.data.database.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Upsert
    suspend fun upsert(player: PlayerEntity)

    @Query("SELECT * FROM players")
    fun getAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players")
    fun getAllList(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE id = :id")
    suspend fun getById(id: String): PlayerEntity

    @Query("DELETE FROM players WHERE id = :id")
    suspend fun deletePlayerById(id: String)

    @Insert
    suspend fun insert(player: PlayerEntity)

    @Insert
    suspend fun insertAll(players: List<PlayerEntity>)

}
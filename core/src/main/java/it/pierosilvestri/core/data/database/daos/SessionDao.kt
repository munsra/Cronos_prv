package it.pierosilvestri.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import it.pierosilvestri.core.data.database.model.SessionEntity

@Dao
interface SessionDao {

    @Upsert
    suspend fun upsert(session: SessionEntity)

    @Query("SELECT * FROM sessions")
    suspend fun getAll(): List<SessionEntity>

    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun getById(id: String): SessionEntity

    @Query("SELECT * FROM sessions WHERE playerId = :playerId")
    suspend fun getSessionsByPlayerId(playerId: String): List<SessionEntity>

    @Insert
    suspend fun insert(session: SessionEntity)

    @Insert
    suspend fun insertAll(sessions: List<SessionEntity>)

    @Query("DELETE FROM sessions WHERE id = :id")
    suspend fun delete(id: String)

}
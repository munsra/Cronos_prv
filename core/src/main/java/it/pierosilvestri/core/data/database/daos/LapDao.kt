package it.pierosilvestri.core.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import it.pierosilvestri.core.data.database.model.LapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LapDao {

    @Upsert
    suspend fun upsert(lap: LapEntity)

    @Query("SELECT * FROM laps")
    fun getAll(): Flow<List<LapEntity>>

    @Query("SELECT * FROM laps WHERE sessionId = :sessionId")
    suspend fun getLapsBySessionId(sessionId: String): List<LapEntity>

    @Insert
    suspend fun insert(lap: LapEntity)

    @Insert
    suspend fun insertAll(laps: List<LapEntity>)

    @Query("DELETE FROM laps WHERE sessionId = :sessionId")
    suspend fun deleteBySessionId(sessionId: String)

    @Query("DELETE FROM laps WHERE id = :id")
    suspend fun deleteLapById(id: String)
}
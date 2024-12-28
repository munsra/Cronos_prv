package it.pierosilvestri.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.pierosilvestri.core.data.database.daos.LapDao
import it.pierosilvestri.core.data.database.daos.PlayerDao
import it.pierosilvestri.core.data.database.daos.PlayerPicturesDao
import it.pierosilvestri.core.data.database.daos.SessionDao
import it.pierosilvestri.core.data.database.model.LapEntity
import it.pierosilvestri.core.data.database.model.PlayerEntity
import it.pierosilvestri.core.data.database.model.PlayerPicturesEntity
import it.pierosilvestri.core.data.database.model.SessionEntity

@Database(
    entities = [
        PlayerEntity::class,
        LapEntity::class,
        SessionEntity::class,
        PlayerPicturesEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun lapDao(): LapDao
    abstract fun sessionDao(): SessionDao
    abstract fun playerPicturesDao(): PlayerPicturesDao


    companion object {
        const val DB_NAME = "stopwatch.db"
    }
}
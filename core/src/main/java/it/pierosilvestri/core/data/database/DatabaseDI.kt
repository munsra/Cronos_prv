package it.pierosilvestri.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import it.pierosilvestri.core.data.database.model.LapEntity
import it.pierosilvestri.core.data.database.model.PlayerEntity
import it.pierosilvestri.core.data.database.model.PlayerPicturesEntity
import it.pierosilvestri.core.data.database.model.SessionEntity

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(resetCallback)
        .build()



val resetCallback = object : RoomDatabase.Callback() {
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        // Clear all tables when the database is opened
        /*
        db.execSQL("DELETE FROM ${LapEntity.TABLE_NAME}")
        db.execSQL("DELETE FROM ${PlayerEntity.TABLE_NAME}")
        db.execSQL("DELETE FROM ${SessionEntity.TABLE_NAME}")
        db.execSQL("DELETE FROM ${PlayerPicturesEntity.TABLE_NAME}")

         */
    }
}


fun providePlayerDao(database: AppDatabase) = database.playerDao()
fun provideSessionDao(database: AppDatabase) = database.sessionDao()
fun provideLapDao(database: AppDatabase) = database.lapDao()
fun providePlayerPicturesDao(database: AppDatabase) = database.playerPicturesDao()
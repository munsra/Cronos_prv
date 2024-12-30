package it.pierosilvestri.core.di

import androidx.room.Room
import it.pierosilvestri.core.data.database.AppDatabase
import it.pierosilvestri.core.data.database.mock.MockDatabase
import it.pierosilvestri.core.data.database.provideDatabase
import it.pierosilvestri.core.data.database.provideLapDao
import it.pierosilvestri.core.data.database.providePlayerDao
import it.pierosilvestri.core.data.database.providePlayerPicturesDao
import it.pierosilvestri.core.data.database.provideSessionDao
import it.pierosilvestri.core.data.network.HttpClientFactory
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import it.pierosilvestri.core.data.repository.PlayerRepositoryImpl
import it.pierosilvestri.core.data.repository.SessionRepositoryImpl
import it.pierosilvestri.core.data.repository.LapRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockPlayerRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockSessionRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockLapRepositoryImpl
import it.pierosilvestri.core.domain.repository.LapRepository
import it.pierosilvestri.core.data.remote.RemotePlayerDataSource
import it.pierosilvestri.core.data.remote.KtorRemotePlayerDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

val coreModule: Module = module {
    single { HttpClientFactory.create() }
    singleOf(::KtorRemotePlayerDataSource).bind<RemotePlayerDataSource>()
}

val databaseModule: Module = module {
    single { provideDatabase(androidContext()) }
    single { providePlayerDao(get()) }
    single { provideSessionDao(get()) }
    single { provideLapDao(get()) }
    single { providePlayerPicturesDao(get()) }
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    singleOf(::SessionRepositoryImpl).bind<SessionRepository>()
    singleOf(::LapRepositoryImpl).bind<LapRepository>()
}

val testDatabaseModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java)
            .allowMainThreadQueries() // For testing only
            .build()
    }
    single { providePlayerDao(get()) }
    single { provideSessionDao(get()) }
    single { provideLapDao(get()) }
    single { providePlayerPicturesDao(get()) }
}

val databaseMockModule: Module = module {
    single { MockDatabase() }
    singleOf(::MockPlayerRepositoryImpl).bind<PlayerRepository>()
    singleOf(::MockSessionRepositoryImpl).bind<SessionRepository>()
    singleOf(::MockLapRepositoryImpl).bind<LapRepository>()
}


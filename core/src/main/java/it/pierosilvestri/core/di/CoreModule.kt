package it.pierosilvestri.core.di

import android.content.Context
import it.pierosilvestri.core.data.database.MockDatabase
import it.pierosilvestri.core.data.network.HttpClientFactory
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import it.pierosilvestri.core.data.repository.mock.MockPlayerRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockSessionRepositoyImpl
import it.pierosilvestri.core.data.repository.mock.MockLapRepositoryImpl
import it.pierosilvestri.core.domain.repository.LapRepository
import it.pierosilvestri.core.data.remote.RemotePlayerDataSource
import it.pierosilvestri.core.data.remote.KtorRemotePlayerDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

val coreModule: Module = module {
    single { MockDatabase() }
    single { HttpClientFactory.create() }
    singleOf(::KtorRemotePlayerDataSource).bind<RemotePlayerDataSource>()
    singleOf(::MockPlayerRepositoryImpl).bind<PlayerRepository>()
    singleOf(::MockSessionRepositoyImpl).bind<SessionRepository>()
    singleOf(::MockLapRepositoryImpl).bind<LapRepository>()
}
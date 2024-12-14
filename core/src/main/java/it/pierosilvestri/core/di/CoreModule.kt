package it.pierosilvestri.core.di

import android.content.Context
import it.pierosilvestri.core.data.database.MockDatabase
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import it.pierosilvestri.core.data.repository.mock.MockPlayerRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockSessionRepositoyImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

val coreModule: Module = module {
    single { MockDatabase() }
    singleOf(::MockPlayerRepositoryImpl).bind<PlayerRepository>()
    single<SessionRepository> { MockSessionRepositoyImpl() }
}
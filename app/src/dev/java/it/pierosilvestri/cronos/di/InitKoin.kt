package it.pierosilvestri.cronos.di

import it.pierosilvestri.core.di.coreModule
import it.pierosilvestri.core.di.databaseMockModule
import it.pierosilvestri.core.di.databaseModule
import it.pierosilvestri.leaderboard_domain.di.leaderboardDomainModule
import it.pierosilvestri.leaderboard_presentation.di.leaderboardPresentationModule
import it.pierosilvestri.stopwatch_domain.di.stopwatchDomainModule
import it.pierosilvestri.stopwatch_presentation.di.stopwatchPresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration?) {
    startKoin {
        config?.invoke(this)
        modules(
            coreModule,
            databaseMockModule,
            stopwatchPresentationModule,
            stopwatchDomainModule,
            leaderboardPresentationModule,
            leaderboardDomainModule,
        )
    }
}
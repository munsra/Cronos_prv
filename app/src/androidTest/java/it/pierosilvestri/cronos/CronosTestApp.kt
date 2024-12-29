package it.pierosilvestri.cronos

import android.app.Application
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import it.pierosilvestri.core.di.coreModule
import it.pierosilvestri.core.di.databaseMockModule
import it.pierosilvestri.cronos.di.initTestKoin
import it.pierosilvestri.leaderboard_domain.di.leaderboardDomainModule
import it.pierosilvestri.leaderboard_presentation.di.leaderboardPresentationModule
import it.pierosilvestri.stopwatch_domain.di.stopwatchDomainModule
import it.pierosilvestri.stopwatch_presentation.di.stopwatchPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CronosTestApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.applicationContext
            )
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
}
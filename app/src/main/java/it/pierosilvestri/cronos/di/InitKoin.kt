package it.pierosilvestri.cronos.di

import it.pierosilvestri.stopwatch_domain.di.stopwatchDomainModule
import it.pierosilvestri.stopwatch_presentation.di.stopwatchPresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(stopwatchPresentationModule, stopwatchDomainModule)
    }
}
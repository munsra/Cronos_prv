package it.pierosilvestri.stopwatch_domain.di

import it.pierosilvestri.stopwatch_domain.services.StopwatchServiceImpl
import it.pierosilvestri.stopwatch_domain.services.StopwatchService
import org.koin.core.module.Module
import org.koin.dsl.module

val stopwatchDomainModule: Module = module {
    single<StopwatchService> { StopwatchServiceImpl() }
}
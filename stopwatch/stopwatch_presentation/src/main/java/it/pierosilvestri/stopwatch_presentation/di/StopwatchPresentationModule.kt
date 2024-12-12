package it.pierosilvestri.stopwatch_presentation.di


import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchViewModel

val stopwatchPresentationModule: Module = module {
    viewModelOf(::StopwatchViewModel)
}
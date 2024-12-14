package it.pierosilvestri.leaderboard_presentation.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardViewModel
import org.koin.core.module.dsl.viewModel

val leaderboardPresentationModule: Module = module {
    viewModelOf(::LeaderboardViewModel)
}